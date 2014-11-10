package ch.hsr.nsg.themenrundgang.applicationService;

import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

public interface NsgApi {
	
	void getItems(ApiCallback<Item[]> callback);
	
	void getSubjects(ApiCallback<Subject[]> callback);
	
	void getBeacons(ApiCallback<Beacon[]> callback);
	
	void getAdditions(ApiCallback<Addition[]> callback);
	
}
