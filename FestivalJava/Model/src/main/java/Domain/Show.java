package Domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Show implements Serializable{
    //id
    private Integer idShow;

    //location of the show
    private String location;

    //date and time
    private Date date;

    //how many tickets are available
    private Integer ticketsAvailable;

    //how many tickets have been sold
    private Integer ticketsSold;

    //artist taking part in the show
    private Artist artist;

    //Constructor
    public Show(Integer idShow, String location, Date date, Integer ticketsAvailable, Integer ticketsSold, Artist artist){
        this.idShow = idShow;
        this.location = location;
        this.date = date;
        this.ticketsAvailable = ticketsAvailable;
        this.ticketsSold = ticketsSold;
        this.artist = artist;
    }

    //getters and setters
    public Integer getIdShow() {
        return idShow;
    }

    public void setIdShow(Integer idShow) {
        this.idShow = idShow;
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


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString(){
        return idShow + " " + location + " " + date + " " +
                ticketsAvailable + " " + ticketsSold + " " + artist.getName();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (!(obj instanceof Show)){
            return false;
        }
        Show show = (Show)obj;
        return this.idShow == show.idShow;
    }
}
