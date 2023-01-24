package ru.maric.licmon.exception.license;

import org.springframework.http.HttpStatus;

public interface LicmonException {

    HttpStatus getReturnCode();

}
