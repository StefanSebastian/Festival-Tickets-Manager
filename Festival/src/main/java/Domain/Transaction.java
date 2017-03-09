package Domain;

/**
 * Created by Sebi on 09-Mar-17.
 */
public class Transaction {
    //id
    private Integer idTransaction;

    //name of client
    private String clientName;

    //number of tickets bought
    private Integer numberOfTickets;

    //id of the show for which the tickets were bought
    private Integer idShow;

    //Constructor
    public Transaction(Integer idTransaction, String clientName, Integer numberOfTickets, Integer idShow){
        this.idTransaction = idTransaction;
        this.clientName = clientName;
        this.numberOfTickets = numberOfTickets;
        this.idShow = idShow;
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

    public Integer getIdShow() {
        return idShow;
    }

    public void setIdShow(Integer idShow) {
        this.idShow = idShow;
    }
}
