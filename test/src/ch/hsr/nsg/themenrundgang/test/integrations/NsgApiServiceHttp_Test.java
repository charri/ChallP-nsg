package ch.hsr.nsg.themenrundgang.test.integrations;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceHttp;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

@SuppressWarnings("unused")
public class NsgApiServiceHttp_Test extends TestCase {

	private NsgApi api;
	
	@Override
	@Before
	public void setUp() throws Exception {
		api = new NsgApiServiceHttp();
	}

	@Test
	public void testAdditions() {
		Addition[] result = api.getAdditions();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}
	
	@Test
	public void testItems() {
		
		Item[] result = api.getItems();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}
	
	@Test
	public void testSubjects() {
		
		Subject[] result = api.getSubjects();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}
	
	@Test
	public void testBeacons() {
		
		Beacon[] result = api.getBeacons();
		
		assertNotNull(result);
		assertTrue(result.length > 0);
	}
}
