package dto;

import Domain.Show;
import Domain.Transaction;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class TransactionDTO {
    private Integer id;
    private String clientName;
    private Integer numberOfTickets;
    private Show show;

    public TransactionDTO(Integer id, String clientName,
                       Integer numberOfTickets, Show show){
        this.id = id;
        this.clientName = clientName;
        this.numberOfTickets = numberOfTickets;
        this.show = show;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
