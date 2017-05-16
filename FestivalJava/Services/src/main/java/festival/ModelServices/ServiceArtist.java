package festival.ModelServices;

import festival.Domain.Artist;
import festival.Interfaces.IArtistRepository;
import festival.ModelServices.Interfaces.IServiceArtist;
import festival.Validation.Exceptions.ValidatorException;
import festival.Validation.ValidatorArtist;

import java.util.List;

/**
 * Created by Sebi on 26-Mar-17.
 */
public class ServiceArtist implements IServiceArtist {
    //repository
    private IArtistRepository repository;

    //validator
    private ValidatorArtist validator;

    public ServiceArtist(IArtistRepository repository, ValidatorArtist validator)
    {
        this.repository = repository;
        this.validator = validator;
    }

    public void save(Artist artist) throws ValidatorException {
        validator.validate(artist);
        repository.save(artist);
    }

    public void delete(Integer id)
    {
        repository.delete(id);
    }

    public void update(Integer id, Artist artist) throws ValidatorException {
        validator.validate(artist);
        repository.update(id, artist);
    }

    public Artist getById(Integer id)
    {
        return repository.getById(id);
    }

    public List<Artist> getAll()
    {
        return repository.getAll();
    }
}
