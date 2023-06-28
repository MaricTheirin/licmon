package ru.maric.licmon.storage;

import ru.maric.licmon.model.License;
import java.util.List;
import java.util.Optional;

public interface ILicenseStorage {

    void save(License license);
    Optional<License> getBySerial(String serial);
    List<License> getAll();
    int getSize();
}
