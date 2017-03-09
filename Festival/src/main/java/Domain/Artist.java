package Domain;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Artist {
    //id
    private Integer idArtist;

    //name
    private String name;

    //Constructor
    public Artist(Integer idArtist, String name){
        this.idArtist = idArtist;
        this.name = name;
    }

    //getters and setters
    public Integer getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(Integer idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return idArtist + " " + name;
    }
}
