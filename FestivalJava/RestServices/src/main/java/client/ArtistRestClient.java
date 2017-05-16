package client;

import festival.Domain.Artist;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Sebi on 16-May-17.
 */
public class ArtistRestClient {
    public static final String URL = "http://localhost:8080/festival/artists";

    private RestTemplate restTemplate = new RestTemplate();

    public Artist[] getAll() {
        return restTemplate.getForObject(URL, Artist[].class);
    }

    public Artist getById(Integer id) {
        return restTemplate.getForObject(URL + "/" + id, Artist.class);
    }

    public Artist save(Artist artist) {
        Artist artistSaved = restTemplate.postForObject(URL, artist, Artist.class);
        return artistSaved;
    }

    public void update(Artist artist) {
        restTemplate.put(URL + "/" + artist.getIdArtist(), artist);
    }

    public void delete(Integer idArtist){
        restTemplate.delete(URL + "/" + idArtist);
    }
}
