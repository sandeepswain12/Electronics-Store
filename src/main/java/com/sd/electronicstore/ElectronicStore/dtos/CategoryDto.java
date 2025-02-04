package com.sd.electronicstore.ElectronicStore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {
    private String categoryId;
    @NotBlank
    @Size(min = 4 ,message = "title must be of minimum 4 characters !!")
    private  String title;
    @NotBlank(message = "description must be required")
    private String description;
    @NotBlank(message = "coverimage must be required")
    private String coverImage;

    public CategoryDto() {
    }

    public CategoryDto(String categoryId, String title, String description, String coverImage) {
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.coverImage = coverImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
