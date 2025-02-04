package com.sd.electronicstore.ElectronicStore.services.impl;

import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.dtos.UserDto;
import com.sd.electronicstore.ElectronicStore.entities.User;
import com.sd.electronicstore.ElectronicStore.exceptions.ResourceNotFoundException;
import com.sd.electronicstore.ElectronicStore.helper.Helper;
import com.sd.electronicstore.ElectronicStore.repositories.UserRepository;
import com.sd.electronicstore.ElectronicStore.services.UserService;
import com.sd.electronicstore.ElectronicStore.validate.ImageNameValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    private Logger logger = (Logger) LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        //Generating unique user id
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given id"));
        user.setUserName(userDto.getUserName());
        user.setUserAbout(userDto.getUserAbout());
        user.setUserGender(userDto.getUserGender());
        user.setUserPassword(userDto.getUserPassword());
        user.setUserImageName(userDto.getUserImageName());

        User updatedUser = userRepository.save(user);

        UserDto updateDto = entityToDto(updatedUser);

        return updateDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given id"));
        String fullPath = imagePath+user.getUserImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            logger.info("user image not found in folder");
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }


        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort =  (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()) ;

        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);
        return response;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with given id"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("User not found with given Email"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByUserNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .userName(savedUser.getUserName())
//                .userPassword(savedUser.getUserPassword())
//                .userEmail(savedUser.getUserEmail())
//                .userGender(savedUser.getUserGender())
//                .userAbout(savedUser.getUserAbout())
//                .userImageName(savedUser.getUserImageName())
//                .build();
        return mapper.map(savedUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .userName(userDto.getUserName())
//                .userPassword(userDto.getUserPassword())
//                .userEmail(userDto.getUserEmail())
//                .userAbout(userDto.getUserAbout())
//                .userGender(userDto.getUserGender())
//                .userImageName(userDto.getUserImageName())
//                .build();
        return mapper.map(userDto,User.class);
    }
}
