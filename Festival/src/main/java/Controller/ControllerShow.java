package Controller;

import Domain.Show;
import Repository.Interfaces.IDatabaseRepository;
import Validation.Exceptions.FormatException;
import Validation.ValidatorShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ControllerShow extends AbstractController<Show,Integer> {
    /*
    Constructor
     */
    public ControllerShow(IDatabaseRepository<Show, Integer> repositoryShow, ValidatorShow validatorShow){
        super(repositoryShow, validatorShow);
    }

    @Override
    public Show formatEntity(String... args) throws FormatException {
        Integer id;
        String location;
        String date;
        Integer ticketsAvailable;
        Integer ticketsSold;
        Integer idArtist;
        try{
            id = Integer.parseInt(args[0]);
            location = args[1];
            date = args[2];
            ticketsAvailable = Integer.parseInt(args[3]);
            ticketsSold = Integer.parseInt(args[4]);
            idArtist = Integer.parseInt(args[5]);
        } catch (NumberFormatException e){
            throw new FormatException("Invalid format. Ids and number of tickets must be integers");
        }
        return new Show(id, location, date, ticketsAvailable, ticketsSold, idArtist);
    }

    @Override
    public Integer formatId(String id) throws FormatException {
        Integer idF;
        try{
            idF = Integer.parseInt(id);
        } catch (NumberFormatException e){
            throw new FormatException("Id must be a number");
        }
        return idF;
    }

    /*
    Gets all shows for an artist
     */
    public List<Show> getShowsForArtist(String idArtist){
        List<String> filters = new ArrayList<>();
        List<String> parameters = new ArrayList<>();

        filters.add("idArtist = ?");
        parameters.add(idArtist);

        return repository.filter(filters, parameters);
    }
}
