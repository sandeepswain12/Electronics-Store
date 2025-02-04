package com.sd.electronicstore.ElectronicStore.controllers;

import com.sd.electronicstore.ElectronicStore.dtos.ApiResponseMessage;
import com.sd.electronicstore.ElectronicStore.dtos.ImageResponse;
import com.sd.electronicstore.ElectronicStore.dtos.PageableResponse;
import com.sd.electronicstore.ElectronicStore.dtos.UserDto;
import com.sd.electronicstore.ElectronicStore.services.FileService;
import com.sd.electronicstore.ElectronicStore.services.UserService;
import com.sd.electronicstore.ElectronicStore.validate.ImageNameValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    private Logger logger = (Logger) LoggerFactory.getLogger(ImageNameValidator.class);

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") String userId) {
        UserDto userDto1 = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        ApiResponseMessage message = new ApiResponseMessage();
        message.setMessage("User is deleted Successfully");
        message.setSuccess(true);
        message.setStatus(HttpStatus.OK);
        //ApiResponseMessage message = ApiResponseMessage.builder().message("User is deleted Successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "userName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> search(@PathVariable String keywords) {
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image , @PathVariable String userId) throws IOException {
        String imageName = fileService.uploadFile(image,imageUploadPath);

        UserDto userDto = userService.getUserById(userId);
        userDto.setUserImageName(imageName);
        userService.updateUser(userDto,userId);

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setImageName(imageName);
        imageResponse.setMessage("image uploaded successfully");
        imageResponse.setStatus(HttpStatus.CREATED);
        imageResponse.setSuccess(true);

        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto userDto = userService.getUserById(userId);
        logger.info("user image name {}",userDto.getUserImageName());
        InputStream resource = fileService.getResource(imageUploadPath,userDto.getUserImageName());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
