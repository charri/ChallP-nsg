package ch.hsr.nsg.themenrundgang.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hsr.nsg.themenrundgang.model.Beacon;

public class FakeBeaconMonitor implements BeaconMonitor {
	private static BeaconUpdateSimulator simulator = new BeaconUpdateSimulator();
	private static Map<Region, Integer> registeredRegions = new HashMap<Region, Integer>();
	private static List<Region> enteredRegions = new ArrayList<Region>();
	private static List<Region> exitedRegions = new ArrayList<Region>();
	
	private MonitoringListenerCallback listener;
	
	@Override
	public void setMonitoringListener(MonitoringListenerCallback listener) {
		this.listener = listener;
	}

	@Override
	public void connect(MonitorReadyCallback callback) {
		FakeBeaconMonitor.simulator.registerBeaconMonitor(this);
		callback.onServiceReady();
	}

	@Override
	public void disconnect() {
		simulator.unregisterBeaconMonitor(this);
	}
	
	@Override
	public void startMonitoring(Region region) {
		if (region != null) {
			for (Region r : FakeBeaconMonitor.registeredRegions.keySet()) {
				if (r.getIdentifier().equals(region.getIdentifier()))
					FakeBeaconMonitor.registeredRegions.remove(r);
			}
			if (!FakeBeaconMonitor.registeredRegions.containsKey(region))
				FakeBeaconMonitor.registeredRegions.put(region, 0);
		}
	}
	
	@Override
	public void stopMonitoring(Region region) {
		if (region != null) {
			for (Region r : FakeBeaconMonitor.registeredRegions.keySet()) {
				if (r.getProximityUUID().equals(region.getProximityUUID())
						&& r.getMajor() == region.getMajor()
						&& r.getMinor() == region.getMinor())
					FakeBeaconMonitor.registeredRegions.remove(r);
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
		for (Region region : FakeBeaconMonitor.registeredRegions.keySet()) {
			for (Beacon detectedBeacon : detectedBeacons) {
				if (region.contains(detectedBeacon)) {
					Integer beaconCount = FakeBeaconMonitor.registeredRegions.get(region);
					if (beaconCount == 0)
						FakeBeaconMonitor.enteredRegions.add(region);
					FakeBeaconMonitor.registeredRegions.put(region, beaconCount + 1);
				}
			}
		}
		
		List<Beacon> lostBeacons = new ArrayList<Beacon>();
		for (Beacon beacon : monitoredBeaconsBefore) {
			if (!monitoredBeacons.contains(beacon))
				lostBeacons.add(beacon);
		}
		exitedRegions = new ArrayList<Region>();
		for (Region region : FakeBeaconMonitor.registeredRegions.keySet()) {
			for (Beacon lostBeacon : lostBeacons) {
				if (region.contains(lostBeacon)) {
					Integer beaconCount = FakeBeaconMonitor.registeredRegions.get(region);
					FakeBeaconMonitor.registeredRegions.put(region, beaconCount - 1);
					beaconCount--;
					if (beaconCount == 0)
						FakeBeaconMonitor.exitedRegions.add(region);
				}
			}
		}
	}
	
	protected void makeListenerCalls() {
		if (listener != null) {
			for (Region region : enteredRegions) {
				listener.onEnterRegion(region);
			}
			for (Region region : exitedRegions) {
				listener.onExitRegion(region);
			}
		}
	}
}