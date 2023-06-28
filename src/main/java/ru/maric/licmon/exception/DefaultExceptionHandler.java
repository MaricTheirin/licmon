package ru.maric.licmon.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maric.licmon.exception.license.LicmonException;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(LicmonException.class)
    protected ResponseEntity<ExceptionMessage> handleLicmonException(
            LicmonException exception,
            HttpServletRequest request
    ) {
        logException(exception, request);
        return new ResponseEntity<>(
                new ExceptionMessage(exception.getMessage(), request.getRequestURI(), exception.getStatus().name()),
                exception.getStatus()
        );
    }

    private void logException(Throwable throwable, HttpServletRequest request) {
        log.debug(
                "В ответ на запрос {}: {} выброшена ошибка: {}",
                request.getMethod(),
                request.getRequestURI(),
                throwable.getMessage()
        );
    }

}
