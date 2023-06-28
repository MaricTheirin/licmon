package ru.maric.licmon.model;

import com._1c.license.activator.data.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class License {

    private String descr;
    private String serial;
    private String pin;
    private String previousPin;
    private Date time;
    private CustomerInfo customerInfo;
    private int userAmount;
    private boolean isServerLicense;

}
