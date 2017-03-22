package Utils;

/**
 * Created by Sebi on 19-Mar-17.
 */
public interface Observable {
    /*
    Adds an observer
     */
    void addObserver(Observer o);

    /*
    Removes an observer
     */
    void removeObserver(Observer o);

    /*
    Notify observers
     */
    void notifyObservers();
}
