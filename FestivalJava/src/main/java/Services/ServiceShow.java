package Services;

import Domain.Show;
import Repository.Interfaces.IShowRepository;
import Validation.Exceptions.ValidatorException;
import Validation.ValidatorShow;

import java.util.List;

/**
 * Created by Sebi on 26-Mar-17.
 */
public class ServiceShow {
    //repository
    private IShowRepository repository;

    //validator
    private ValidatorShow validator;

    public ServiceShow(IShowRepository repository, ValidatorShow validator)
    {
        this.repository = repository;
        this.validator = validator;
    }

    public void save(Show show) throws ValidatorException {
        validator.validate(show);
        repository.save(show);
    }

    public void delete(int id)
    {
        repository.delete(id);
    }

    public void update(int id, Show show) throws ValidatorException {
        validator.validate(show);
        repository.update(id, show);
    }

    public Show getById(int id)
    {
        return repository.getById(id);
    }

    public List<Show> getAll()
    {
        return repository.getAll();
    }

    //gets all shows for a given artist
    public List<Show> getShowsForArtist(Integer idArtist)
    {
        return repository.getShowsForArtist(idArtist);
    }


    //gets all shows for a date
    public List<Show> getShowsForDate(String date)
    {
        return repository.getShowsForDate(date);
    }
}
