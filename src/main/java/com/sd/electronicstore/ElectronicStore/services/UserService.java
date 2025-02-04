package com.sd.electronicstore.ElectronicStore.services;


import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.dtos.UserDto;

import java.util.List;

public interface UserService {
    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto user, String userId);

    //delete
    void deleteUser(String userId);

    //get all user
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get user by id
    UserDto getUserById(String userId);

    //get user by email
    UserDto getUserByEmail(String userEmail);

    List<UserDto> searchUser(String keyword);
    //other
}
