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

    @ExceptionHandler(ValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidException(ValidException e) {
        log.warn("Ошибка валидации: " + e.getMessage());
        return Map.of("Ошибка валидации: ", e.getMessage());
    }

    @ExceptionHandler(HandlerNullPointException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNullPointer(HandlerNullPointException e) {
        log.warn("Ошибка: " + e.getMessage());
        return Map.of("Ошибка: ", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String, String> handleNullPointer1(MethodArgumentNotValidException e) {
        String exceptionMessage = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        log.warn("Ошибка: " + exceptionMessage);
        return Map.of("Ошибка валидации: ", exceptionMessage);
    }
}
