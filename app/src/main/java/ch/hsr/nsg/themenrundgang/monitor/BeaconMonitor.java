package ch.hsr.nsg.themenrundgang.monitor;

import android.os.RemoteException;

public interface BeaconMonitor {
    void setMonitoringListener(MonitoringListenerCallback listener);
    void connect(MonitorReadyCallback callback);
    void disconnect();
    void startMonitoring(Region region) throws RemoteException;
    void stopMonitoring(Region region) throws RemoteException;
}