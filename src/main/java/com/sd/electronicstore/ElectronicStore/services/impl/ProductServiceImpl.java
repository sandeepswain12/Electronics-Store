package com.sd.electronicstore.ElectronicStore.services.impl;

import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.dtos.ProductDto;
import com.sd.electronicstore.ElectronicStore.entities.Product;
import com.sd.electronicstore.ElectronicStore.exceptions.ResourceNotFoundException;
import com.sd.electronicstore.ElectronicStore.helper.Helper;
import com.sd.electronicstore.ElectronicStore.repositories.ProductRepository;
import com.sd.electronicstore.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Value("${product.image.path}")
    private String imageUploadPath;


    @Override
    public ProductDto create(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto,Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with this "+productId+ " id not found"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setStock(productDto.isStock());
        product.setProductImage(productDto.getProductImage());
        Product updatedProduct = productRepository.save(product);
        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with this "+productId+ " id not found"));
        String fullPath = imageUploadPath+product.getProductImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        productRepository.delete(product);
    }

    @Override
    public PageableResponse<ProductDto> getall(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with this "+productId+ " id not found"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> search(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(page,ProductDto.class);
        return response;
    }
}
