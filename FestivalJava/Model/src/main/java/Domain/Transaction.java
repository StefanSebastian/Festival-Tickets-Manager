package Domain;

import java.io.Serializable;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Transaction implements Serializable {
    //id
    private Integer idTransaction;

    //name of client
    private String clientName;

    //number of tickets bought
    private Integer numberOfTickets;

    //show for which the tickets were bought
    private Show show;

    //Constructor
    public Transaction(Integer idTransaction, String clientName, Integer numberOfTickets, Show show){
        this.idTransaction = idTransaction;
        this.clientName = clientName;
        this.numberOfTickets = numberOfTickets;
        this.show = show;
    }

    //Constructor with default id
    public Transaction(String clientName, Integer numberOfTickets, Show show){
        this.idTransaction = 0;
        this.clientName = clientName;
        this.numberOfTickets = numberOfTickets;
        this.show = show;
    }


    //getters and setters
    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
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


    @Override
    public String toString(){
        return idTransaction + " " + clientName + " " +
                numberOfTickets + " " + show.getIdShow();
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
