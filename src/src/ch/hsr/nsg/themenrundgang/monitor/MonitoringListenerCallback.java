package ch.hsr.nsg.themenrundgang.monitor;

public interface MonitoringListenerCallback {
	void onEnterRegion(Integer major, Integer minor);
	void onExitRegion(Integer major, Integer minor);
}
