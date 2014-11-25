package ch.hsr.nsg.themenrundgang.monitor;

public interface MonitoringListenerCallback {
	void onEnterRegion(Region region);
	void onExitRegion(Region region);
}
