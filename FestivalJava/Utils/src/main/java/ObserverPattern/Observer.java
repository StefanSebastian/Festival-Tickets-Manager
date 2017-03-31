package ObserverPattern;

/**
 * Created by Sebi on 30-Mar-17.
 */
public interface Observer<E> {
    /*
    Updates observer
     */
    void update(E e);
}
