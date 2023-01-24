package ru.maric.licmon.storage;

import java.util.List;

public interface Storage<T> {

    T getById(Integer id);
    List<T> getAll();

}
