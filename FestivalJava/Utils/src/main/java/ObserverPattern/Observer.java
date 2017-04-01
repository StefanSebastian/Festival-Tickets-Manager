package ObserverPattern;

/**
 * Created by Sebi on 30-Mar-17.
 */
public interface Observer<E> {
    /*
    Updates push observer
     */
    void updatePush(E e);

    /*
    Updates observer
     */
    void update();
}
