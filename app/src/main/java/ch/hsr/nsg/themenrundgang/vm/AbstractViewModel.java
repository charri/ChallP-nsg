package ch.hsr.nsg.themenrundgang.vm;

import java.util.Observable;
import java.util.Observer;

import ch.hsr.nsg.themenrundgang.utils.OnObservable;

public abstract class AbstractViewModel implements OnObservable {

    private Observable observable;

    public AbstractViewModel() {
        observable = new Observable();
    }

    @Override
    public void notifyObservers(String key) {
        observable.hasChanged();
        observable.notifyObservers(key);
    }
    @Override
    public void addObserver(Observer observer) {
        observable.addObserver(observer);
    }
    @Override
    public void deleteObserver(Observer observer) {
        observable.deleteObserver(observer);
    }
}
