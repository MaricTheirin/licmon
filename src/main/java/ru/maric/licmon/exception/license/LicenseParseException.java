package ru.maric.licmon.exception.license;

import org.springframework.http.HttpStatus;

public class LicenseParseException extends LicmonException {

    public LicenseParseException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
