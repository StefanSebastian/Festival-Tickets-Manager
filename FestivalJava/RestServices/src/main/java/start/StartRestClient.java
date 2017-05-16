package start;

import client.ArtistRestClient;
import festival.Domain.Artist;

/**
 * Created by Sebi on 16-May-17.
 */
public class StartRestClient {
    private final static ArtistRestClient client = new ArtistRestClient();

    public static void main(String[] args) {
        System.out.println("Get all : localhost:8080/festival/artists");
        Artist[] artists = null;
        artists = client.getAll();
        for (Artist artist1 : artists){
            System.out.println(artist1.getIdArtist() + " " + artist1.getName());
        }


        System.out.println("Get by id : localhost:8080/festival/artists/10");
        Artist artist = client.getById(10);
        System.out.println(artist.getIdArtist() + " " + artist.getName());

        System.out.println("Save an artist: localhost:8080/festival/artists POST");
        Artist artistN = new Artist("Artist");
        Artist saved = client.save(artistN);
        for (Artist artist1 : client.getAll()){
            System.out.println(artist1.getIdArtist() + " " + artist1.getName());
        }

        System.out.println("Updates an artist: localhost:8080/festival/artists/" + saved.getIdArtist() +" PUT");
        client.update(new Artist(saved.getIdArtist(), "updated!"));
        for (Artist artist1 : client.getAll()){
            System.out.println(artist1.getIdArtist() + " " + artist1.getName());
        }

        System.out.println("Deletes an artist: localhost:8080/festival/artists/" + saved.getIdArtist() +" DELETE");
        client.delete(saved.getIdArtist());
        for (Artist artist1 : client.getAll()){
            System.out.println(artist1.getIdArtist() + " " + artist1.getName());
        }
    }
}
