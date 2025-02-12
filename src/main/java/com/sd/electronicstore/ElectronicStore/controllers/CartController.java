package com.sd.electronicstore.ElectronicStore.controllers;

import com.sd.electronicstore.ElectronicStore.dtos.AddItemToCartRequest;
import com.sd.electronicstore.ElectronicStore.dtos.ApiResponseMessage;
import com.sd.electronicstore.ElectronicStore.dtos.CartDto;
import com.sd.electronicstore.ElectronicStore.entities.CartItem;
import com.sd.electronicstore.ElectronicStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
        cartService.removeItemFromCart(userId,itemId);
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setMessage("Item Removed");
        apiResponseMessage.setStatus(HttpStatus.OK);
        apiResponseMessage.setSuccess(true);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setMessage("Now cart is empty");
        apiResponseMessage.setStatus(HttpStatus.OK);
        apiResponseMessage.setSuccess(true);
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
