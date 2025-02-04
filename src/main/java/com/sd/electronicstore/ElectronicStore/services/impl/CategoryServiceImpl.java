package com.sd.electronicstore.ElectronicStore.services.impl;

import com.sd.electronicstore.ElectronicStore.dtos.CategoryDto;
import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.entities.Category;
import com.sd.electronicstore.ElectronicStore.exceptions.ResourceNotFoundException;
import com.sd.electronicstore.ElectronicStore.helper.Helper;
import com.sd.electronicstore.ElectronicStore.repositories.CategoryRepository;
import com.sd.electronicstore.ElectronicStore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${category.image.path}")
    private String imageUploadPath;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with this "+categoryId+" id"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategories = categoryRepository.save(category);
        return mapper.map(updatedCategories,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with this "+categoryId+" id"));
        String fullPath = imageUploadPath+category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort =  (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page,CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category not found with this "+categoryId+" id"));
        return mapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> search(String keyword) {
        List<Category> categoryList = categoryRepository.findByTitleContaining(keyword);
        List<CategoryDto> categoryDtoList = categoryList.stream()
                .map(category -> mapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtoList;
    }
}
