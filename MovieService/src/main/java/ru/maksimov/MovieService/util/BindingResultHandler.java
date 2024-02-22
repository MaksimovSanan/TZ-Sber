package ru.maksimov.MovieService.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Утилитарный класс для обработки объекта BindingResult.
 */
public class BindingResultHandler {

    /**
     * Обработка объекта BindingResult и возврат сообщений об ошибках в виде строки.
     *
     * @param bindingResult Объект BindingResult для обработки.
     * @return Строка с сообщениями об ошибках.
     */
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
