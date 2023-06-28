package ru.maric.licmon.exception;

import lombok.Value;
import java.time.LocalDateTime;

@Value
public class ExceptionMessage {
    String error;
    LocalDateTime localDateTime = LocalDateTime.now();
    String path;
    String status;
}
