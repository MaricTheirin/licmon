package ru.maric.licmon.storage;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maric.licmon.model.License;
import java.util.*;

@Service
@Slf4j
@NoArgsConstructor
public class InMemoryLicenseStorage implements ILicenseStorage {

    private final Map<String, License> licenses = new HashMap<>();

    @Override
    public Optional<License> getBySerial(String serial) {
        Optional<License> license = Optional.ofNullable(licenses.get(serial));
        log.trace("Получена лицензия {}", license);
        return license;
    }

    @Override
    public List<License> getAll() {
        List<License> allLicenses = new ArrayList<>(licenses.values());
        log.trace("Получен список лицензий: {}", allLicenses);
        return allLicenses;
    }

    @Override
    public void save(License license) {
        log.trace("Сохранена лицензия {}", license);
        licenses.put(license.getSerial(), license);
    }

    @Override
    public int getSize() {
        return licenses.size();
    }

}
