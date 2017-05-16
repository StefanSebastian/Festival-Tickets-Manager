package festival.ModelServices.Interfaces;

import festival.Domain.Show;
import festival.Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IServiceShow {
    /*
    Saves a show
     */
    void save(Show show) throws ValidatorException;

    /*
    Deletes a show
     */
    void delete(Integer idShow);

    /*
    Updates a show
     */
    void update(Integer id, Show show) throws ValidatorException;

    /*
    Gets a show by id
     */
    Show getById(Integer idShow);

    /*
    Returns a list of shows
     */
    List<Show> getAll();

    /*
    Returns all shows for a given artist
     */
    List<Show> getShowsForArtist(Integer idArtist);

    /*
    Returns all shows for a given date
     */
    List<Show> getShowsForDate(String date);
}
