package Repository.Interfaces;

import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public interface IDatabaseRepository<E, ID> extends IRepository<E, ID> {
    /*
    Applies a list of filters and returns the result of the query
     */
    List<E> filter(List<String> filters, List<String> arguments);

    /*
    Saves the entity without its id, the id is assigned by the database
    (if set to auto increment)
     */
    void saveWithoutId(E entity);
}
