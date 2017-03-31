package dto;

import java.io.Serializable;

/**
 * Created by Sebi on 30-Mar-17.
 */
public class BuyTicketsDTO implements Serializable {
    private Integer idShow;
    private String clientName;
    private Integer numberOfTickets;

    public BuyTicketsDTO(Integer idShow, String clientName, Integer numberOfTickets) {
        this.idShow = idShow;
        this.clientName = clientName;
        this.numberOfTickets = numberOfTickets;
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

}
