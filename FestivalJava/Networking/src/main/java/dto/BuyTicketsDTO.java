package dto;

import java.io.Serializable;

/**
 * Created by Sebi on 30-Mar-17.
 *
 * Used to transfer data for a transaction
 */
public class BuyTicketsDTO implements Serializable {
    private Integer idShow;
    private String clientName;
    private Integer numberOfTickets;
    private String username;

    public BuyTicketsDTO(Integer idShow, String clientName, Integer numberOfTickets, String username) {
        this.idShow = idShow;
        this.clientName = clientName;
        this.numberOfTickets = numberOfTickets;
        this.username = username;
    }

    public Integer getIdShow() {
        return idShow;
    }

    public void setIdShow(Integer idShow) {
        this.idShow = idShow;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
