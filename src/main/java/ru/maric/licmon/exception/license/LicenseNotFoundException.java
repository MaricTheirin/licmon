package ru.maric.licmon.exception.license;

import org.springframework.http.HttpStatus;

public class LicenseNotFoundException extends LicmonException {

    public LicenseNotFoundException(String serial) {
        super("Лицензия с serial = " + serial + " не найдена", HttpStatus.NOT_FOUND);
    }

}
