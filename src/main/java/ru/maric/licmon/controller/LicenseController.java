package ru.maric.licmon.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maric.licmon.model.License;
import ru.maric.licmon.service.LicenseService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/licenses")
public class LicenseController {

    private final LicenseService licenseService;

    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping
    public List<License> getAllLicenses() {
        return licenseService.getAll();
    }

    @GetMapping("/{id}")
    public License getLicenseById(@PathVariable String id) {
        return licenseService.getById(id);
    }

}
