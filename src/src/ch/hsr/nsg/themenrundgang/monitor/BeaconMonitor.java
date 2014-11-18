package ch.hsr.nsg.themenrundgang.monitor;

public interface BeaconMonitor {
	void setMonitoringListener(MonitoringListenerCallback listener);
	void startMonitoring();
	void stop();
}
