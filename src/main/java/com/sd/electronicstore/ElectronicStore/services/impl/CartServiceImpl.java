package com.sd.electronicstore.ElectronicStore.services.impl;

import com.sd.electronicstore.ElectronicStore.dtos.AddItemToCartRequest;
import com.sd.electronicstore.ElectronicStore.dtos.CartDto;
import com.sd.electronicstore.ElectronicStore.entities.Cart;
import com.sd.electronicstore.ElectronicStore.entities.CartItem;
import com.sd.electronicstore.ElectronicStore.entities.Product;
import com.sd.electronicstore.ElectronicStore.entities.User;
import com.sd.electronicstore.ElectronicStore.exceptions.BadApiRequestException;
import com.sd.electronicstore.ElectronicStore.exceptions.ResourceNotFoundException;
import com.sd.electronicstore.ElectronicStore.repositories.CartItemRepository;
import com.sd.electronicstore.ElectronicStore.repositories.CartRepository;
import com.sd.electronicstore.ElectronicStore.repositories.ProductRepository;
import com.sd.electronicstore.ElectronicStore.repositories.UserRepository;
import com.sd.electronicstore.ElectronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {
            throw new BadApiRequestException("Requested quantity is not valid !!");
        }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in database !!"));
        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedDate(new Date());
        }

        //perform cart operations
        //if cart items already present; then update
        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        //cart.setItems(updatedItems);



        //create items
        if (!updated.get()) {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(quantity * product.getDiscountedPrice());
            cartItem.setProduct(product);
            cart.getItems().add(cartItem);
        }


        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        CartItem cartItem1 = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found !!"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));

        return mapper.map(cart, CartDto.class);
    }
}
