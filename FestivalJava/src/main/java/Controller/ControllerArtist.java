package Controller;

import Domain.Artist;
import Repository.Interfaces.IDatabaseRepository;
import Validation.Exceptions.FormatException;
import Validation.ValidatorArtist;

/**
 * Created by Sebi on 18-Mar-17.
 */
public class ControllerArtist extends AbstractController<Artist, Integer> {
    /*
    Constructor
     */
    public ControllerArtist(IDatabaseRepository<Artist, Integer> repositoryArtist,
                            ValidatorArtist validatorArtist){
        super(repositoryArtist, validatorArtist);
    }

    @Override
    public Artist formatEntity(String... args) throws FormatException {
        Integer id;
        String name;
        try{
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            throw new FormatException("Id must be a number");
        }
        name = args[1];
        return new Artist(id, name);
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
}
