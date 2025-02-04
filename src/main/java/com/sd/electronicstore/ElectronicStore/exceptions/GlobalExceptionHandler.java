package com.sd.electronicstore.ElectronicStore.exceptions;

import com.sd.electronicstore.ElectronicStore.dtos.ApiResponseMessage;
import com.sd.electronicstore.ElectronicStore.validate.ImageNameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = (Logger) LoggerFactory.getLogger(ImageNameValidator.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> ResourceNotFoundExceptionsHandler(ResourceNotFoundException ex){
        logger.info("exception handler invoker");
        ApiResponseMessage responseMessage = new ApiResponseMessage();
        responseMessage.setMessage(ex.getMessage());
        responseMessage.setSuccess(true);
        responseMessage.setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequestException ex){
        logger.info("Bad api request");
        ApiResponseMessage responseMessage = new ApiResponseMessage();
        responseMessage.setMessage(ex.getMessage());
        responseMessage.setSuccess(false);
        responseMessage.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }
}
