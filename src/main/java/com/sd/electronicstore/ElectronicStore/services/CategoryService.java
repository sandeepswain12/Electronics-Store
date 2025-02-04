package com.sd.electronicstore.ElectronicStore.services;

import com.sd.electronicstore.ElectronicStore.dtos.CategoryDto;
import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.entities.Category;

import java.util.List;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);
    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);
    //delete
    void delete(String categoryId);
    //getall
    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
    //get by id
    CategoryDto get(String categoryId);
    //search
    List<CategoryDto> search(String keyword);
}
