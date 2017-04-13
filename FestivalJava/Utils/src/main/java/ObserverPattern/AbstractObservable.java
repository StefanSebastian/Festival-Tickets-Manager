package ObserverPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 30-Mar-17.
 */
public abstract class AbstractObservable<E> implements Observable<E> {
    private List<Observer<E>> observers = new ArrayList<Observer<E>>();

    @Override
    public void addObserver(Observer<E> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<E> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(){
        for (Observer<E> observer : observers){
            observer.update();
        }
    }
}
