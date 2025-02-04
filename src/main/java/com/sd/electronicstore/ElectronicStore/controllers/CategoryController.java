package com.sd.electronicstore.ElectronicStore.controllers;

import com.sd.electronicstore.ElectronicStore.dtos.ApiResponseMessage;
import com.sd.electronicstore.ElectronicStore.dtos.CategoryDto;
import com.sd.electronicstore.ElectronicStore.dtos.ImageResponse;
import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.services.CategoryService;
import com.sd.electronicstore.ElectronicStore.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        CategoryDto updatedCategory = categoryService.update(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage();
        apiResponseMessage.setMessage("Category deleted successfully");
        apiResponseMessage.setSuccess(true);
        apiResponseMessage.setStatus(HttpStatus.OK);
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return new ResponseEntity<>(categoryService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    //getSingle
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable String categoryId){
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword){
        return new ResponseEntity<>(categoryService.search(keyword),HttpStatus.OK);
    }
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage")MultipartFile image,@PathVariable String categoryId) throws IOException {
        String imageName = fileService.uploadFile(image,imageUploadPath);
        CategoryDto categoryDto = categoryService.get(categoryId);
        categoryDto.setCoverImage(imageName);
        categoryService.update(categoryDto,categoryId);

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setMessage("image uploaded successfully");
        imageResponse.setImageName(imageName);
        imageResponse.setStatus(HttpStatus.CREATED);
        imageResponse.setSuccess(true);

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = categoryService.get(categoryId);
        InputStream resource = fileService.getResource(imageUploadPath,categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
