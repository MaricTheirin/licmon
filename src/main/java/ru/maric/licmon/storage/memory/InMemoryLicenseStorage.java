package ru.maric.licmon.storage.memory;

import org.springframework.stereotype.Service;
import ru.maric.licmon.model.License;
import ru.maric.licmon.storage.LicenseStorage;

import java.util.List;

@Service
public class InMemoryLicenseStorage implements LicenseStorage {

    public InMemoryLicenseStorage() {
    }

    @Override
    public License getById(Integer id) {
        return null;
    }

    @Override
    public List<License> getAll() {
        return null;
    }

}
