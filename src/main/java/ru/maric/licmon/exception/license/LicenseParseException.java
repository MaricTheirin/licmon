package ru.maric.licmon.exception.license;

import org.springframework.http.HttpStatus;

public class LicenseParseException extends RuntimeException implements LicmonException {

    private static final HttpStatus returnCode = HttpStatus.INTERNAL_SERVER_ERROR;

    public LicenseParseException(String message) {
        super(message);
    }

    public HttpStatus getReturnCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
