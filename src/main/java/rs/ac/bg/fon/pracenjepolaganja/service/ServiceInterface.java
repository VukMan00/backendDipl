package rs.ac.bg.fon.pracenjepolaganja.service;

import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> findAll();
    T findById(Object id) throws NotFoundException;
    T save(T t);
    void deleteById(Object id) throws NotFoundException;
}
