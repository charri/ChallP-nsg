package ch.hsr.nsg.themenrundgang.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.monitor.BeaconMonitor;
import ch.hsr.nsg.themenrundgang.monitor.FakeBeaconMonitor;
import ch.hsr.nsg.themenrundgang.monitor.MonitorReadyCallback;
import ch.hsr.nsg.themenrundgang.monitor.MonitoringListenerCallback;
import ch.hsr.nsg.themenrundgang.monitor.Region;

public class ListBeaconsActivity extends Activity {
	private BeaconMonitor beaconMonitor;

	private final Handler myHandler = new Handler();
	private ListView mListView;
	private ArrayAdapter<String> adapter;
	private final ArrayList<String> beaconList = new ArrayList<String>();

	private final static int[] NOF_BEACONS_PER_LOCATION = {1, 3, 2, 2, 2, 4};
	 
	private static final List<Region> ALL_BEACON_REGIONS = new ArrayList<Region>();
	private static final List<Region> ALL_LOCATION_REGIONS = new ArrayList<Region>();
	private static final Region MUSEUM_REGION = new Region("region_museum", null, null);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.beaconMonitor = new FakeBeaconMonitor();
		initializeAllBeaconRegions();
		initializeAllLocationRegions();
		/*try {
			this.beaconMonitor.startMonitoring(MUSEUM_REGION);
		} catch (RemoteException e) {
			e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Cannot start ranging", Toast.LENGTH_LONG).show();
		}*/
		
		setContentView(R.layout.activity_list_beacons);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, beaconList);

		mListView = (ListView) findViewById(R.id.listView1);
		mListView.setAdapter(adapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		beaconMonitor.setMonitoringListener(new BeaconListener());
		
		beaconMonitor.connect(new MonitorReadyCallback() {
			@Override
			public void onServiceReady() {
				startMonitoringBeacons();
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		beaconMonitor.disconnect();
	}

	private void initializeAllBeaconRegions() {
		for (int i = 0; i < NOF_BEACONS_PER_LOCATION.length; i++) {
			for (int j = 0; j < NOF_BEACONS_PER_LOCATION[i]; j++) {
				Integer beaconId = j;
				Integer locationId = (i + 1);
				String regionId = "region_beacon_" + locationId + "/" + beaconId;
				Region beaconRegion = new Region(regionId, locationId, beaconId);
				ALL_BEACON_REGIONS.add(beaconRegion);
			}
		}
	}

	private void startMonitoringBeacons() {
		for (Region beaconRegion : ALL_BEACON_REGIONS) {
			try {
				this.beaconMonitor.startMonitoring(beaconRegion);
			} catch (RemoteException e) {
				e.printStackTrace();
				Log.i("startMonitoringBeacons", "Cannot start ranging");
	            Toast.makeText(getApplicationContext(), "Cannot start ranging", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	private void initializeAllLocationRegions() {
		for (int i = 0; i < NOF_BEACONS_PER_LOCATION.length; i++) {
			Integer locationId = (i + 1);
			String regionId = "region_location_" + locationId;
			Region locationRegion = new Region(regionId, locationId, null);
			ALL_LOCATION_REGIONS.add(locationRegion);
			/*try {
				this.beaconMonitor.startMonitoring(locationRegion);
			} catch (RemoteException e) {
				e.printStackTrace();
	            Toast.makeText(getApplicationContext(), "Cannot start ranging", Toast.LENGTH_LONG).show();
			}*/
		}
	}
	
	private class ListUpdater implements Runnable {
		String listItem; 
		boolean removing;
		
		private ListUpdater(String listItem, boolean removing) {
			this.listItem = listItem;
			this.removing = removing;
		}
		
		@Override
		public void run() {
			if (listItem != null) {
				if (removing) {
					beaconList.remove(listItem);
					Log.i("onExitRegion", listItem);
				} else {
					beaconList.add(listItem);
					Log.i("onEnterRegion", listItem);
				}
				adapter.notifyDataSetChanged();
			}
		}
		
	}
	
	private class BeaconListener implements MonitoringListenerCallback {
		@Override
		public void onEnterRegion(Region region) {
			if (region.getMinor() != null) {
				myHandler.post(new ListUpdater(region.toString(), false));
			}
		}
		
		@Override
		public void onExitRegion(Region region) {
			if (region.getMinor() != null) {
				myHandler.post(new ListUpdater(region.toString(), true));
			}
		}
	}
}
