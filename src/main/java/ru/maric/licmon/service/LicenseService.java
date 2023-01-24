package ru.maric.licmon.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maric.licmon.exception.license.LicenseParseException;
import ru.maric.licmon.model.License;
import ru.maric.licmon.storage.memory.InMemoryLicenseStorage;
import com._1c.license.activator.crypt.Converter;
import com._1c.license.activator.data.LicensePermit;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LicenseService {

    InMemoryLicenseStorage licenseStorage;
    private static final String WINDOWS_PATH_TO_LOCAL_LICENSES = "C:\\ProgramData\\1C\\licenses";

    @Autowired
    public LicenseService(InMemoryLicenseStorage licenseStorage) {
        this.licenseStorage = licenseStorage;
    }

    public List<License> getAll() {
        log.debug("Выполнен запрос всех лицензий");
        List<License> licenses = Arrays.stream(new File(WINDOWS_PATH_TO_LOCAL_LICENSES).listFiles())
                .filter(file -> !file.isDirectory())
                .map(this::readLicenseFromServer)
                .collect(Collectors.toList());
        log.debug("Получен список из {} лицензий", licenses.size());
        log.trace("Перечень: {}", licenses.stream().map(License::getSerial).toString());
        return licenses;
    }

    public License getById(Integer id) {
        log.debug("Выполнен запрос лицензии с id = {}", id);
        return null;
    }

    private License readLicenseFromServer(File file) {
        License license = decodeLicenseFromBase64(readLicenseRawInBase64(file));
        log.trace("Декодирована лицензия: {}", license);
        return license;
    }

    private static String readLicenseRawInBase64(File file) {
        String licenseEncodedInBase64;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            StringBuilder licenseRaw = new StringBuilder();
            String s;

            while ((s = reader.readLine()) != null) {
                licenseRaw.append(s);
            }
            int cutStart = licenseRaw.indexOf(":");
            if (cutStart != -1)
                licenseRaw.delete(cutStart, licenseRaw.length());
            licenseEncodedInBase64 = licenseRaw.toString();
        } catch (IOException e) {
            log.warn("Ошибка при парсинге файла: {}. Текст ошибки: {}", file.getName(), e.getMessage());
            throw new LicenseParseException("Ошибка при разборе файла лицензии: " + file.getName());
        }
        return licenseEncodedInBase64;
    }

    private License decodeLicenseFromBase64(String licenseRawEncodedInBase64) {
        Converter converter = new Converter();

        LicensePermit licensePermit = converter.getLicensePermitFromBase64(licenseRawEncodedInBase64);
        return License.builder()
                .descr(licensePermit.getBox().getDescr())
                .serial(licensePermit.getRequest().getSerial())
                .customerInfo(licensePermit.getRequest().getCustomer())
                .time(licensePermit.getRequest().getTime())
                .pin(licensePermit.getRequest().getPin())
                .previousPin(licensePermit.getRequest().getPreviousPin())
                .userAmount(licensePermit.getUsersAmount())
                .build();
    }

}
