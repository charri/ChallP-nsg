package ch.hsr.nsg.themenrundgang.vm;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import ch.hsr.nsg.themenrundgang.utils.OnObservable;

public abstract class AbstractViewModel extends Observable implements OnObservable {

    private final static String TAG = "OnObservable";

    public AbstractViewModel() {

    }

    @Override
    public void notifyOn(String key) {
        Log.i(TAG, "notifyObservers " + key);
        Log.i(TAG, "registered " + countObservers());
        setChanged();
        notifyObservers(key);
    }
}
