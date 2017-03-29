package dto;

import Domain.Artist;

import java.io.Serializable;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class ShowDTO implements Serializable {
    private Integer id;
    private String location;
    private String date;
    private Integer ticketsAvailable;
    private Integer ticketsSold;
    private Artist artist;

    public ShowDTO(Integer id, String location, String date,
                   Integer ticketsAvailable, Integer ticketsSold,
                   Artist artist){
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}