package ru.maric.licmon.exception.license;

import org.springframework.http.HttpStatus;

public abstract class LicmonException extends RuntimeException {

    private final HttpStatus status;

    public LicmonException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
