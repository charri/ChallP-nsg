package ch.hsr.nsg.themenrundgang.utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;


public class OnManager implements OnObserver {

    private final static String TAG = "OnManger";

    private HashMap<String, LinkedList<OnListener>> mMutex;

    public OnManager() {
        mMutex = new HashMap<>();
    }

    @Override
    public void update(Observable observable, Object data) {
        String key = data.toString();

        Log.i(TAG, "update " + key);
        Log.i(TAG, "mMutex containsKey " + mMutex.containsKey(key));

        if(!mMutex.containsKey(key)) return;

        for(OnListener li : mMutex.get(key)) {
            li.onNotify();
        }
    }
    @Override
    public void addOnListener(String key, OnListener listener) {
        if(!mMutex.containsKey(key)) {
            mMutex.put(key, new LinkedList<OnListener>());
        }

        mMutex.get(key).add(listener);
    }
    @Override
    public boolean removeOnListener(String key, OnListener listener) {
        if(!mMutex.containsKey(key)) return false;

        Iterator iterator = mMutex.get(key).iterator();

        boolean found = false;

        while(iterator.hasNext()) {
            OnListener li = (OnListener)iterator.next();
            if(li.equals(listener)) {
                iterator.remove();
                found = true;
            }
        }

        return found;
    }
}
