package ru.yandex.practicum.filmorate.Exceptions;

public class DataNotExistsException extends RuntimeException{

    public DataNotExistsException(String message) {
        super(message);
    }
}
