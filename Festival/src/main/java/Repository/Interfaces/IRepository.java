package Repository.Interfaces;

import java.util.List;

/**
 * Created by Sebi on 09-Mar-17.
 */
public interface IRepository<E, ID> {
    /*
    Saves an entity
     */
    void save(E entity);

    /*
    Deletes an entity
     */
    void delete(ID id);

    /*
    Updates an entity
     */
    void update(ID id, E entity);

    /*
    Gets the entity with the given id
     */
    E getById(ID id);

    /*
    Returns all entities
     */
    List<E> getAll();
}
