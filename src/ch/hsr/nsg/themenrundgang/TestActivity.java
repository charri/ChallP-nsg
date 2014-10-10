package ch.hsr.nsg.themenrundgang;

import java.util.ArrayList;
import java.util.List;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TestActivity extends Activity {
	
	private BeaconManager beaconManager;
	private ListView mListView;
	
	private ArrayAdapter<String> adapter;
	 
	private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		beaconManager = new BeaconManager(this);
		
		setContentView(R.layout.activity_test);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		mListView = (ListView)findViewById(R.id.listView1);
		mListView.setAdapter(adapter);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
			
			@Override
			public void onExitedRegion(Region arg0) {
				adapter.clear();
				mListView.setAdapter(adapter);
			}
			
			@Override
			public void onEnteredRegion(Region arg0, List<Beacon> arg1) {
				for(Beacon b : arg1) {
					adapter.add(b.toString());
					Log.i("onEnterRegion", b.toString());
					
				}
				adapter.add(arg0.toString());
				Log.i("onEnterRegion Region", arg0.toString());
				mListView.setAdapter(adapter);
			}
		});
		
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startMonitoring(ALL_ESTIMOTE_BEACONS_REGION);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		beaconManager.disconnect();
	}
	
}
