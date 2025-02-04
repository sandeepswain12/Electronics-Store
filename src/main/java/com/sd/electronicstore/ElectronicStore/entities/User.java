package com.sd.electronicstore.ElectronicStore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "uid")
    private String userId;
    @Column(name = "uname")
    private String userName;
    @Column(name = "uemail" , unique = true)
    private String userEmail;
    @Column(name = "upassword",length = 10)
    private String userPassword;
    @Column(name = "ugender")
    private String userGender;
    @Column(name = "uabout",length = 1000)
    private String userAbout;
    @Column(name = "uimagename")
    private String userImageName;

    public User() {

    }

    public User(String userId, String userName, String userEmail, String userPassword, String userGender, String userAbout, String userImageName) {
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
