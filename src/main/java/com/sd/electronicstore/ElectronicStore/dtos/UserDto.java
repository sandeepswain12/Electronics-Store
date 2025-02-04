package com.sd.electronicstore.ElectronicStore.dtos;


import com.sd.electronicstore.ElectronicStore.validate.ImageNameValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


public class UserDto {
    private String userId;
    @Size(min = 3,max = 20, message = "Invalid size of name")
    private String userName;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "invalid mail")
    private String userEmail;
    @NotBlank(message = "password required")
    private String userPassword;
    @Size(min = 4,max = 6,message = "invalid size")
    private String userGender;
    @NotBlank(message = "Write something about yourself")
    private String userAbout;
    @ImageNameValid
    private String userImageName;

    public UserDto() {

    }

    public UserDto(String userId, String userName, String userEmail, String userPassword, String userGender, String userAbout, String userImageName) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userAbout = userAbout;
        this.userImageName = userImageName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public String getUserImageName() {
        return userImageName;
    }

    public void setUserImageName(String userImageName) {
        this.userImageName = userImageName;
    }
}
