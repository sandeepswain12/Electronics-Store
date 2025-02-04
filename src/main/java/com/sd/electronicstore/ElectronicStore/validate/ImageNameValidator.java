package com.sd.electronicstore.ElectronicStore.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {
    private Logger logger = (Logger) LoggerFactory.getLogger(ImageNameValidator.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        logger.info("invalid email",value);

        if (value.isBlank()) {
            return false;
        } else {
            return true;
        }
    }
}
