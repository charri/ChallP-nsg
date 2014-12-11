package ch.hsr.nsg.themenrundgang.monitor;

import java.util.List;

import android.content.Context;
import android.os.RemoteException;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.estimote.sdk.BeaconManager.ServiceReadyCallback;

public class BeaconMonitorEstimote implements BeaconMonitor {

    BeaconManager beaconManager;

    public BeaconMonitorEstimote(Context context) {
        beaconManager = new BeaconManager(context);
    }

    @Override
    public void setMonitoringListener(final MonitoringListenerCallback listener) {
        beaconManager.setMonitoringListener(new MonitoringListener() {
            @Override
            public void onEnteredRegion(com.estimote.sdk.Region estimoteRegion, List<com.estimote.sdk.Beacon> estimoteBeacons) {
                Region region = new Region(estimoteRegion.getIdentifier(), estimoteRegion.getMajor(), estimoteRegion.getMinor());
                listener.onEnterRegion(region);
            }

            @Override
            public void onExitedRegion(com.estimote.sdk.Region estimoteRegion) {
                Region region = new Region(estimoteRegion.getIdentifier(), estimoteRegion.getMajor(), estimoteRegion.getMinor());
                listener.onEnterRegion(region);
            }
        });
    }

    @Override
    public void connect(final MonitorReadyCallback callback) {
        this.beaconManager.connect(new ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                callback.onServiceReady();
            }
        });
    }

    @Override
    public void disconnect() {
        beaconManager.disconnect();
    }

    @Override
    public void startMonitoring(Region region) throws RemoteException {
        com.estimote.sdk.Region estimoteRegion = new com.estimote.sdk.Region(region.getIdentifier(), region.getProximityUUID(), region.getMajor(), region.getMinor());
        beaconManager.startMonitoring(estimoteRegion);
    }

    @Override
    public void stopMonitoring(Region region) throws RemoteException {
        com.estimote.sdk.Region estimoteRegion = new com.estimote.sdk.Region(region.getIdentifier(), region.getProximityUUID(), region.getMajor(), region.getMinor());
        beaconManager.stopMonitoring(estimoteRegion);
    }
}