package ch.hsr.nsg.themenrundgang.monitor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import ch.hsr.nsg.themenrundgang.model.Beacon;

public class BeaconMonitorFake implements BeaconMonitor {
    private static BeaconScannerFake simulator;
    private static ConcurrentHashMap<Region, Integer> registeredRegions = new ConcurrentHashMap<Region, Integer>();
    private static List<Region> enteredRegions = new ArrayList<Region>();
    private static List<Region> exitedRegions = new ArrayList<Region>();

    private MonitoringListenerCallback listener;
    private Context context;
    private ScannerServiceConnection serviceConnection;

    public BeaconMonitorFake(Context context) {
        this.context = context;
    }

    @Override
    public void setMonitoringListener(MonitoringListenerCallback listener) {
        this.listener = listener;
    }

    @Override
    public void connect(MonitorReadyCallback callback) {
		/*if (BeaconMonitorFake.simulator == null) {
			context.startService(intent);
			
		}*/
        //context.startService(intent);
        Intent intent = new Intent(context, BeaconScannerFake.class);
        serviceConnection = new ScannerServiceConnection(callback);
        boolean isBound = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		/*if (isBound) {

		}*/
    }

    @Override
    public void disconnect() {
        BeaconMonitorFake.simulator.unregisterBeaconMonitor(this);
        context.unbindService(serviceConnection);
    }

    @Override
    public void startMonitoring(Region region) {
        if (region != null) {
            for (Region r : BeaconMonitorFake.registeredRegions.keySet()) {
                if (r.getIdentifier().equals(region.getIdentifier()))
                    BeaconMonitorFake.registeredRegions.remove(r);
            }
            if (!BeaconMonitorFake.registeredRegions.containsKey(region))
                BeaconMonitorFake.registeredRegions.put(region, 0);
        }
    }

    @Override
    public void stopMonitoring(Region region) {
        if (region != null) {
            for (Region r : BeaconMonitorFake.registeredRegions.keySet()) {
                if (r.getProximityUUID().equals(region.getProximityUUID())
                        && r.getMajor() == region.getMajor()
                        && r.getMinor() == region.getMinor())
                    BeaconMonitorFake.registeredRegions.remove(r);
            }
        }
    }

    protected static void updateMonitor(List<Beacon> monitoredBeaconsBefore, List<Beacon> monitoredBeacons) {
        List<Beacon> detectedBeacons = new ArrayList<Beacon>();
        for (Beacon beacon : monitoredBeacons) {
            if (!monitoredBeaconsBefore.contains(beacon))
                detectedBeacons.add(beacon);
        }
        enteredRegions = new ArrayList<Region>();
        for (Region region : BeaconMonitorFake.registeredRegions.keySet()) {
            for (Beacon detectedBeacon : detectedBeacons) {
                if (region.contains(detectedBeacon)) {
                    Integer beaconCount = BeaconMonitorFake.registeredRegions.get(region);
                    if (beaconCount == 0)
                        BeaconMonitorFake.enteredRegions.add(region);
                    BeaconMonitorFake.registeredRegions.put(region, beaconCount + 1);
                }
            }
        }

        List<Beacon> lostBeacons = new ArrayList<Beacon>();
        for (Beacon beacon : monitoredBeaconsBefore) {
            if (!monitoredBeacons.contains(beacon))
                lostBeacons.add(beacon);
        }
        exitedRegions = new ArrayList<Region>();
        for (Region region : BeaconMonitorFake.registeredRegions.keySet()) {
            for (Beacon lostBeacon : lostBeacons) {
                if (region.contains(lostBeacon)) {
                    Integer beaconCount = BeaconMonitorFake.registeredRegions.get(region);
                    BeaconMonitorFake.registeredRegions.put(region, beaconCount - 1);
                    beaconCount--;
                    if (beaconCount == 0)
                        BeaconMonitorFake.exitedRegions.add(region);
                }
            }
        }
    }

    protected void makeListenerCalls() {
        if (listener != null) {
            for (Region region : enteredRegions) {
                Log.i("onEnterRegion", region.toString());
                listener.onEnterRegion(region);
            }
            for (Region region : exitedRegions) {
                Log.i("onExitRegion", region.toString());
                listener.onExitRegion(region);
            }
        }
    }

    protected static void resetSimulator() {
        BeaconMonitorFake.simulator = null;
    }

    private class ScannerServiceConnection implements ServiceConnection {
        private MonitorReadyCallback onServiceConnectedCallback;

        protected ScannerServiceConnection(MonitorReadyCallback onServiceConnectedCallback) {
            this.onServiceConnectedCallback = onServiceConnectedCallback;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder) {

            if (BeaconMonitorFake.simulator == null)
                BeaconMonitorFake.simulator = ((BeaconScannerFake.BeaconScannerBinder) serviceBinder).getScanner();
            BeaconMonitorFake.simulator.registerBeaconMonitor(BeaconMonitorFake.this);
            onServiceConnectedCallback.onServiceReady();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { }
    }
}