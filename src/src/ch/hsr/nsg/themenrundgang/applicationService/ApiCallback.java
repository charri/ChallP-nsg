package ch.hsr.nsg.themenrundgang.applicationService;

public interface ApiCallback<TResult> {
	void result(TResult result);
	void failure();
}
