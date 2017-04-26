package dto;

import java.io.Serializable;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class ArtistDTO implements Serializable {
    private Integer id;
    private String name;

    public ArtistDTO(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
