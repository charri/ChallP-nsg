package ch.hsr.nsg.themenrundgang.monitor;

import java.util.List;

import android.content.Context;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.BeaconManager.MonitoringListener;
import com.estimote.sdk.Region;

public class EstimoteBeaconMonitor implements BeaconMonitor {
	
	BeaconManager beaconManager;
	
	public EstimoteBeaconMonitor(Context context) {
		beaconManager = new BeaconManager(context);
	}

	@Override
	public void startMonitoring() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setMonitoringListener(final MonitoringListenerCallback listener) {
		beaconManager.setMonitoringListener(new MonitoringListener() {
			@Override
			public void onEnteredRegion(Region region, List<Beacon> beacons) {
				// TODO if (region.getProximityUUID().equals("naturmuseumsg")
				listener.onEnterRegion(region.getMajor(), region.getMinor());
			}

			@Override
			public void onExitedRegion(Region region) {
				// TODO if (region.getProximityUUID().equals("naturmuseumsg")
				listener.onEnterRegion(region.getMajor(), region.getMinor());
			}
		});
	}

	@Override
	public void stop() {
		beaconManager.disconnect();
	}
}
