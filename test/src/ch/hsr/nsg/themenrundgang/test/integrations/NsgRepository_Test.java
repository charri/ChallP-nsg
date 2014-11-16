package ch.hsr.nsg.themenrundgang.test.integrations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceFake;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceHttp;
import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

public class NsgRepository_Test extends AndroidTestCase {

	private NsgRepository repository;	
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = new NsgRepository(getContext());
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		getContext().deleteDatabase(repository.getDatabaseName());
	}
	
	@Test
	public void testSyncFake() {
		NsgApi api = new NsgApiServiceFake();
		
		loadfromApi(api);
	}
	
	private void loadfromApi(NsgApi api) {
		for ( Subject s : api.getSubjects()) {
			repository.insertOrUpdate(s);
		}
		
		for (Beacon b : api.getBeacons()) {
			repository.insertOrUpdate(b);
		}
		
		for(Item i : api.getItems()) {
			repository.insertOrUpdate(i);
		}
		
		for(Addition a : api.getAdditions()) {
			repository.insertOrUpdate(a);
		}
	}
	
	@Test
	public void testSyncHttp() {
		NsgApi api = new NsgApiServiceHttp();
		
		loadfromApi(api);
	}
}
