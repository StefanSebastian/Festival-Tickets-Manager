package dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class ShowDTO implements Serializable {
    private Integer id;
    private String location;
    private Date date;
    private Integer ticketsAvailable;
    private Integer ticketsSold;
    private ArtistDTO artist;

    public ShowDTO(Integer id, String location, Date date,
                   Integer ticketsAvailable, Integer ticketsSold,
                   ArtistDTO artist){
        this.id = id;
        this.location = location;
        this.date = date;
        this.ticketsAvailable = ticketsAvailable;
        this.ticketsSold = ticketsSold;
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(Integer ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public Integer getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Integer ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }
}
