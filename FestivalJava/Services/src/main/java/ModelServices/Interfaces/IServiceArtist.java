package ModelServices.Interfaces;

import Domain.Artist;
import Validation.Exceptions.ValidatorException;

import java.util.List;

/**
 * Created by Sebi on 28-Mar-17.
 */
public interface IServiceArtist {
    /*
    Saves an artist
     */
    void save(Artist artist) throws ValidatorException;

    /*
    Deletes an artist
     */
    void delete(Integer idArtist);

    /*
    Updates an artist
     */
    void update(Integer id, Artist artist) throws ValidatorException;

    /*
    Returns an artist by id
     */
    Artist getById(Integer idArtist);

    /*
    Returns all artists
     */
    List<Artist> getAll();
}
