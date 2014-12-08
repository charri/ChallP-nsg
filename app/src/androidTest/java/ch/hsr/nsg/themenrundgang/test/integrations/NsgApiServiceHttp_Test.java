package ch.hsr.nsg.themenrundgang.test.integrations;

import android.app.Application;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceHttp;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

@SuppressWarnings("unused")
public class NsgApiServiceHttp_Test extends ApplicationTestCase<Application> {

    public NsgApiServiceHttp_Test() {
        super(Application.class);
    }

	private NsgApi api;
	
	@Override
	public void setUp() throws Exception {
		api = new NsgApiServiceHttp();
	}

	public void testAdditions() {
		Addition[] result = api.getAdditions();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}

	public void testItems() {
		
		Item[] result = api.getItems();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}
	
	public void testSubjects() {
		
		Subject[] result = api.getSubjects();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}

	public void testBeacons() {
		
		Beacon[] result = api.getBeacons();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}
}
