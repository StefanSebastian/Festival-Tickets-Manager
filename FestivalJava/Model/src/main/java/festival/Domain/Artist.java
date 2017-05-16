package festival.Domain;

import java.io.Serializable;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Artist implements Serializable{
    //id
    private Integer idArtist;

    //name
    private String name;

    public Artist(){}

    public Artist(String name){
        this.idArtist = 0;
        this.name = name;
    }

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
        return name;
    }
}
