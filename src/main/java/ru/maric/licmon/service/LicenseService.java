package ru.maric.licmon.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maric.licmon.exception.license.LicenseNotFoundException;
import ru.maric.licmon.exception.license.LicenseParseException;
import ru.maric.licmon.model.License;
import com._1c.license.activator.crypt.Converter;
import com._1c.license.activator.data.LicensePermit;
import ru.maric.licmon.storage.ILicenseStorage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class LicenseService {

    private static final Path WINDOWS_PATH_TO_LOCAL_LICENSES = Paths.get("C:\\ProgramData\\1C\\licenses");
    private static final Path LINUX_PATH_TO_LOCAL_LICENSES = Paths.get("/var/1c/licenses");

    private final ILicenseStorage licenseStorage;

    public LicenseService(ILicenseStorage licenseStorage) {
        this.licenseStorage = licenseStorage;
        parseAllLicenses();
    }

    public List<License> getAll() {
        log.debug("Выполнен запрос всех лицензий");
        return licenseStorage.getAll();
    }

    public License getById(String serial) {
        log.debug("Выполнен запрос лицензии с серийным номером = {}", serial);
        return licenseStorage.getBySerial(serial).orElseThrow(() -> new LicenseNotFoundException(serial));
    }

    private void parseAllLicenses() {
        try (Stream<Path> files = Files.walk(getDefaultLicenseStorage(), 1)) {
            files.filter(path -> !Files.isDirectory(path))
                .map(Path::toFile)
                .forEach(lic -> licenseStorage.save(readLicenseFromServer(lic)));
        } catch (IOException e) {
            throw new LicenseParseException("Ошибка при парсинге лицензий: " + e.getMessage());
        }
        log.debug("Сохранены данные {} лицензий", licenseStorage.getSize());
    }

    private Path getDefaultLicenseStorage() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return WINDOWS_PATH_TO_LOCAL_LICENSES;
        } else if (os.contains("nix")) {
            return LINUX_PATH_TO_LOCAL_LICENSES;
        } else {
            throw new LicenseParseException("Определение лицензий возможно только на ОС семейств Windows и Linux");
        }
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
