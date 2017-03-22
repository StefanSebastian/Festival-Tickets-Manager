package Domain;

/**
 * Created by Sebi on 19-Mar-17.
 * Auxiliary class used for displaying data about shows and artist in UI tables
 */
public class ShowArtist {
    //info about show
    private Integer idShow;
    private String location;
    private String date;
    private Integer ticketsAvailable;
    private Integer ticketsSold;

    //info about artist
    private Integer idArtist;
    private String artistName;

    //constructor
    public ShowArtist(Integer idShow, String location, String date, Integer ticketsAvailable,
                      Integer ticketsSold, Integer idArtist, String artistName){
        this.idShow = idShow;
        this.location = location;
        this.date = date;
        this.ticketsAvailable = ticketsAvailable;
        this.ticketsSold = ticketsSold;
        this.idArtist = idArtist;
        this.artistName = artistName;
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

    public Integer getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(Integer idArtist) {
        this.idArtist = idArtist;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
