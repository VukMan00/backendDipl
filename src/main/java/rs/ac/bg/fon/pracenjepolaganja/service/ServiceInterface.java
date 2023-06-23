package rs.ac.bg.fon.pracenjepolaganja.service;

import rs.ac.bg.fon.pracenjepolaganja.exception.type.NotFoundException;

import java.util.List;

/**
 * Represent service layer who has access to repository of entity classes.
 * Controllers call this service layer when they need access to database.
 * Contains methods findAll, findById, save and deleteById.
 * @param <T> generic parameter, is replaced with entity class objects
 *
 * @author Vuk Manojlovic
 */
public interface ServiceInterface<T> {

    /**
     * Retrieves all entities, from database, of type that is provided in T parameter.
     * Entities are sent back in DTO form.
     *
     * @return list of entities from database.
     */
    List<T> findAll();

    /**
     * Retrieves one entity, from database, of type that is provided in T parameter.
     * Entity is sent back in DTO form.
     *
     * @param id id of entity that is needed. It's the Integer or object that represent complex primary key of some entities.
     * @return entity of the provided id in DTO form.
     * @throws NotFoundException if entity with given id does not exist in database
     *
     */
    T findById(Object id) throws NotFoundException;

    /**
     * Saves the entity of type that is provided in T parameter.
     *
     * @param t entity that needs to be saved
     * @return entity that is saved in DTO form
     * @throws NullPointerException if provided entity is null
     * @throws org.springframework.security.authentication.BadCredentialsException Only occur when email for Student entity
     * is not in valid form.
     */
    T save(T t);

    /**
     * Deletes the entity of type that is provided in T parameter.
     *
     * @param id id of entity that is going to be deleted. It's the Integer or object that represent complex primary key of some entities.
     * @throws NotFoundException if entity with given id does not exist in database
     */
    void deleteById(Object id) throws NotFoundException;
}
