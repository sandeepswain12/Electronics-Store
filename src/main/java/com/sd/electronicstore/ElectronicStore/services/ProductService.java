package com.sd.electronicstore.ElectronicStore.services;

import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.dtos.ProductDto;



public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,String productId);

    //delete
    void delete(String productId);

    //getall
    PageableResponse<ProductDto> getall(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get by id
    ProductDto get(String productId);
    
    //search
    PageableResponse<ProductDto> search(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

    //get all live
    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    //create with category
    ProductDto createWithCategory(ProductDto productDto ,String categoryId);

    //assign product to category
    ProductDto updateCategory(String productId ,String categoryId);

    PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
}
