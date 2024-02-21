package ru.maksimov.MovieService.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BindingResultHandler {
    public static String handlingBindingResult(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
        }
        return errorMsg.toString();
    }
}
