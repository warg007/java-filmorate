package ru.yandex.practicum.filmorate.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(HandlerNullPointException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNullPointer(HandlerNullPointException e) {
        log.warn("Ошибка: " + e.getMessage());
        return Map.of("Ошибка: ", e.getMessage());
    }

    @ExceptionHandler(DataNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleDataExists(DataNotExistsException e) {
        log.warn("Ошибка: " + e.getMessage());
        return Map.of("Ошибка: ", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidPointer(MethodArgumentNotValidException e) {
        String exceptionMessage = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        log.warn("Ошибка валидации: " + exceptionMessage);
        return Map.of("Ошибка валидации: ", exceptionMessage);
    }
}
