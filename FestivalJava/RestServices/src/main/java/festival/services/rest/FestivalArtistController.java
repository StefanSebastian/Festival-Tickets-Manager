package festival.services.rest;

import festival.Domain.Artist;
import festival.Interfaces.IArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by Sebi on 16-May-17.
 */
@RestController
@RequestMapping("/festival/artists")
public class FestivalArtistController {
    @Autowired
    private IArtistRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Artist> getAll(){
        return repository.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Artist artist = repository.getById(id);
        if (artist == null){
            return new ResponseEntity<String>("Artist not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Artist>(artist, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Artist create(@RequestBody Artist artist){
        repository.save(artist);
        return artist;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        repository.delete(id);
        return new ResponseEntity<Artist>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public Artist update(@RequestBody Artist artist, @PathVariable Integer id){
        Artist artistNew = new Artist(id, artist.getName());
        repository.update(id, artistNew);
        return artistNew;
    }
}
