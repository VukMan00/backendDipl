package rs.ac.bg.fon.pracenjepolaganja.service;

import rs.ac.bg.fon.pracenjepolaganja.dto.AnswerDTO;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> findAll();
    T findById(Object id);
    T save(T t);
    void deleteById(Integer id);
}
