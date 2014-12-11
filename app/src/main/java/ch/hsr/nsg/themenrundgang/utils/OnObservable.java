package ch.hsr.nsg.themenrundgang.utils;


import java.util.Observer;

public interface OnObservable {
    public void notifyObservers(String key);
    public void addObserver(Observer observer);
    public void deleteObserver(Observer observer);
}
