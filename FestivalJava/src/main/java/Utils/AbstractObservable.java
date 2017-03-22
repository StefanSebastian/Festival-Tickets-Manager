package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebi on 19-Mar-17.
 */
public abstract class AbstractObservable implements Observable {
    private List<Observer> observerList = new ArrayList<>();

    @Override
    public void addObserver(Observer o){
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o){
        observerList.remove(o);
    }

    @Override
    public void notifyObservers(){
        for (Observer o : observerList){
            o.update();
        }
    }
}
