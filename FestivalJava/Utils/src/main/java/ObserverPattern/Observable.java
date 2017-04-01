package ObserverPattern;

/**
 * Created by Sebi on 30-Mar-17.
 */
public interface Observable<E> {
    /*
    Adds an observer
     */
    void addObserver(Observer<E> o);

    /*
    Removes an observer
     */
    void removeObserver(Observer<E> o);

    /*
    Notifies push observers
     */
    void notifyPushObservers(E e);

    /*
    Notifies observers
     */
    void notifyObservers();
}