package com.example.task.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@UtilityClass
@Slf4j
public class ErrorUtil {

    public static void returnErrorsToClient(BindingResult bindingResult) throws Exception {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                    .append(";");
        }
        log.error(errorMsg.toString());
        throw new Exception(errorMsg.toString());
    }
}