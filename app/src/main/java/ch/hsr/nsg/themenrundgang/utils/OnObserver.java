package ch.hsr.nsg.themenrundgang.utils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by tommy on 11.12.14.
 */
public interface OnObserver extends Observer {
    public void addOnListener(String key, OnListener listener);
    public boolean removeOnListener(String key, OnListener listener);
}
