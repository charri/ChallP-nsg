package ch.hsr.nsg.themenrundgang.monitor;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.nsg.themenrundgang.model.Beacon;

public class BeaconScannerFake extends Service {
    private final static int SCAN_INTERVALL = 2500;
    private final static int MIN_SCANS_FOR_BEACONS_UPDATE = 1;
    private final static int MAX_SCANS_FOR_BEACONS_UPDATE = 2;
    private final static int MIN_BEACONS_UPDATES_FOR_LOCATION_CHANGE = 3;
    private final static int MAX_BEACONS_UPDATES_FOR_LOCATION_CHANGE = 6;

    private final static int MIN_NOF_BEACONS = 1;
    private final static int MAX_NOF_BEACONS = 5;
    private final static int[] NOF_BEACONS_PER_LOCATION = {1, 3, 2, 2, 2, 4};
    private final static double ENTERED_LOCATION_WEIGHT	= 0.7;

    private Thread monitorUpdater;

    private List<Beacon> monitoredBeacons = new ArrayList<Beacon>();
    private List<Integer> sortedLocationIds = new ArrayList<Integer>();

    private List<BeaconMonitorFake> listeningBeaconMonitors = new ArrayList<BeaconMonitorFake>();

    private boolean running = false;

    protected void registerBeaconMonitor(BeaconMonitorFake listener) {
        listeningBeaconMonitors.add(listener);
    }

    protected void unregisterBeaconMonitor(BeaconMonitorFake listener) {
        listeningBeaconMonitors.remove(listener);

        if (listeningBeaconMonitors.size() == 0) {
            BeaconMonitorFake.resetSimulator();
			
			
			/*try {
				monitorUpdater.wait();
			} catch (InterruptedException e) {
		         monitorUpdater.interrupt(); 
			}*/
            running = false;
        }
    }

    private void updateLocations() {
        int totalNofLocations = NOF_BEACONS_PER_LOCATION.length;

        List<Integer> availableLocationIds = new ArrayList<Integer>();
        for (int i = 1; i <= totalNofLocations; i++) {
            availableLocationIds.add(i);
        }

        List<Integer> monitoredLocationIds = new ArrayList<Integer>();
        Integer locationId = getRandomNumberInRange(1, totalNofLocations);;
        for (int i = 0; i < totalNofLocations; i++) {
            locationId = getRandomNumberInList(availableLocationIds);
            monitoredLocationIds.add(locationId);
            availableLocationIds.remove(locationId);
        }

        this.sortedLocationIds = monitoredLocationIds;
    }

    private void updateBeacons() {
        int nofDetectedBeacons = getRandomNumberInRange(MIN_NOF_BEACONS, MAX_NOF_BEACONS);
        if (nofDetectedBeacons == 0) {
            List<Beacon> monitoredBeaconsBefore = this.monitoredBeacons;
            this.monitoredBeacons = new ArrayList<Beacon>();
            if (this.monitoredBeacons.size() > 0) {
                BeaconMonitorFake.updateMonitor(monitoredBeaconsBefore, this.monitoredBeacons);
                for (BeaconMonitorFake listener : listeningBeaconMonitors) {
                    listener.makeListenerCalls();
                }
            }
        } else {
            int enteredLocationId = sortedLocationIds.get(0);
            int beaconsInEnteredLocation = (int) Math.ceil(nofDetectedBeacons * ENTERED_LOCATION_WEIGHT);
            beaconsInEnteredLocation = Math.min(beaconsInEnteredLocation, NOF_BEACONS_PER_LOCATION[enteredLocationId - 1]);

            List<Beacon> monitoredBeacons = new ArrayList<Beacon>();
            monitoredBeacons = updateBeaconsInEnteredLocation(enteredLocationId, beaconsInEnteredLocation);
            monitoredBeacons.addAll(updateBeaconsOutsideLocation(nofDetectedBeacons - beaconsInEnteredLocation));
            List<Beacon> monitoredBeaconsBefore = this.monitoredBeacons;
            this.monitoredBeacons = monitoredBeacons;
            BeaconMonitorFake.updateMonitor(monitoredBeaconsBefore, this.monitoredBeacons);
            for (BeaconMonitorFake listener : listeningBeaconMonitors) {
                listener.makeListenerCalls();
            }
        }
    }

    private List<Beacon> updateBeaconsInEnteredLocation(int enteredLocationId, int beaconsInEnteredLocation) {
        List<Beacon> monitoredBeacons = new ArrayList<Beacon>();
        while (monitoredBeacons.size() != beaconsInEnteredLocation) {
            int beaconId = getRandomNumberInRange(0, NOF_BEACONS_PER_LOCATION[enteredLocationId - 1]);
            Beacon beacon = new Beacon(enteredLocationId, beaconId);
            if (!monitoredBeacons.contains(beacon)) {
                monitoredBeacons.add(beacon);
            }
        }

        return monitoredBeacons;
    }

    private List<Beacon> updateBeaconsOutsideLocation(int nofDetectedBeacons) {
        ArrayList<Beacon> monitoredBeacons = new ArrayList<Beacon>();
        int locationIndex = 1;
        int locationId = sortedLocationIds.get(locationIndex);
        int beaconsInLocation = 0;
        while (nofDetectedBeacons != monitoredBeacons.size()) {
            if (beaconsInLocation >= NOF_BEACONS_PER_LOCATION[locationId - 1]) {
                locationId = sortedLocationIds.get(++locationIndex);
                beaconsInLocation = 0;
            }

            int beaconId = getRandomNumberInRange(0, NOF_BEACONS_PER_LOCATION[locationId - 1]);
            Beacon beacon = new Beacon(locationId, beaconId);
            if (!monitoredBeacons.contains(beacon)) {
                monitoredBeacons.add(beacon);
                beaconsInLocation++;
            }
        }

        return monitoredBeacons;
    }

    private int getRandomNumberInRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    private Integer getRandomNumberInList(List<Integer> set) {
        return set.get((int) (Math.random() * set.size()));
    }

    private class MonitorUpdater implements Runnable {

        @Override
        public void run() {
            int scansForBeaconsUpdate;
            int scansForLocationUpdate;

            while (running) {
                scansForBeaconsUpdate = getRandomNumberInRange(MIN_SCANS_FOR_BEACONS_UPDATE, MAX_SCANS_FOR_BEACONS_UPDATE);
                scansForLocationUpdate = scansForBeaconsUpdate *
                        Math.max(MIN_BEACONS_UPDATES_FOR_LOCATION_CHANGE, MAX_BEACONS_UPDATES_FOR_LOCATION_CHANGE);

                for (int i = 1; (running && i <= scansForLocationUpdate); i++) {
                    if ((i % scansForLocationUpdate) == 0)
                        updateLocations();
                    if ((i % scansForBeaconsUpdate) == 0)
                        updateBeacons();

                    try {
                        Thread.sleep(SCAN_INTERVALL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        run();
                    }
                }
            }
        }
    }

    @Override
    public void onCreate() {
        updateLocations();

        this.monitorUpdater = new Thread(new MonitorUpdater());
        monitorUpdater.start();

        running = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BeaconScannerBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
		/*if (listeningBeaconMonitors.size() == 0) {
			
		}*/

        boolean onRebindCalled = true;
        return onRebindCalled;
    }

    protected class BeaconScannerBinder extends Binder {
        protected BeaconScannerFake getScanner() {
            return BeaconScannerFake.this;
        }
		/*
		protected void registerMonitor(BeaconMonitorFake listener) {
			BeaconScannerFake.this.listeningBeaconMonitors.add(listener);
		}*/
    }
}