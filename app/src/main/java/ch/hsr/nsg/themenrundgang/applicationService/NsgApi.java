package ch.hsr.nsg.themenrundgang.applicationService;

import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

public interface NsgApi {
	
	Item[] getItems();
	
	void getItemsAsync(ApiCallback<Item[]> callback);
	
	Subject[] getSubjects();
	
	void getSubjectsAsync(ApiCallback<Subject[]> callback);
	
	Beacon[] getBeacons();
	
	void getBeaconsAsync(ApiCallback<Beacon[]> callback);
	
	Addition[] getAdditions();
	
	void getAdditionsAsync(ApiCallback<Addition[]> callback);

    String getImagePath(int imageId);
}
