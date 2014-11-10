package ch.hsr.nsg.themenrundgang.applicationService;


import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;

public class NsgApiServiceFake implements NsgApi {

	@Override
	public void getItems(ApiCallback<Item[]> callback) {
		if(callback == null) return;
		
		Item[] items = new Item[5];
		items[0] = newItem(1, "Geologische Karte der Kantone SG, AI, AR", "Unsere Region hat ein vielfältige und zum Teil recht komplexe Geologie. Dies hängt mit ihrer Entstehungsgeschichte zusammen.", new int[] { 1 }, new String[] { "1.0" }, new int[] { 1 });
		items[1] = newItem(2, "Braunbär", "Der Braunbär (Ursus arctos) ist eine Säugetierart aus der Familie der Bären (Ursidae). Er kommt in mehreren Unterarten – darunter Europäischer Braunbär (U. a. arctos), Grizzlybär (U. a. horribilis) und Kodiakbär (U. a. middendorffi) – in Eurasien und Nordamerika vor.", new int[] { 2 }, new String[] { "2.0" }, new int[] { 2, 3 });
		items[2] = newItem(3, "Grosser Panda", "Der Große Panda (Ailuropoda melanoleuca), oft auch einfach als Pandabär bezeichnet, ist eine Säugetierart aus der Familie der Bären (Ursidae). Als Symbol des WWF und manchmal auch des Artenschutzes allgemein hat er trotz seines sehr beschränkten Verbreitungsgebiets weltweite Bekanntheit erlangt. In älterer deutscher Literatur wird der Große Panda auch Bambusbär oder Prankenbär genannt.", new int[] { 2 }, new String[] { "2.0", "2.1" }, new int[] { });
		items[3] = newItem(4, "Tisch mit Bährennahrung", "Bären sind meist Allesfresser, die je nach Art und Jahreszeit in unterschiedlichem Ausmaß pflanzliche und tierische Nahrung zu sich nehmen. ", new int[] { 2 }, new String[] { "2.1" }, new int[] { });
		items[4] = newItem(5, "Bährenhöhle", "Bären sind Einzelgänger und führen generell eine eher dämmerungs- oder nachtaktive Lebensweise (mit Ausnahme des Eisbären)", new int[] { 2 }, new String[] { "2.1" }, new int[] { });
		
		callback.result(items);
	}
	
	private Item newItem(int id, String name, String description, int[] subjects, String[] beacons, int[] images) {
		Item item = new Item();
		item.setId(id);
		item.setName(name);
		item.setDescription(description);
		item.setSubjects(subjects);
		item.setBeacons(beacons);
		item.setImages(images);
		return item;
	}

	@Override
	public void getSubjects(ApiCallback<Subject[]> callback) {
		
		if(callback == null) return;
		
		Subject[] subjects = new Subject[5];
		
		subjects[0] = newSubject(1, 0, "Gesteine als Untergrund", "");
		subjects[1] = newSubject(2, 0, "Bären", "");
		subjects[2] = newSubject(3, 0, "Menschen", "");
		subjects[3] = newSubject(4, 0, "Bodenschätze", "");
		
		callback.result(subjects);
	}
	
	private Subject newSubject(int id, int parentId, String name, String description) {
		Subject subject = new Subject();
		subject.setId(id);
		subject.setName(name);
		subject.setDescription(description);
		subject.setParentId(parentId);
		return subject;
	}

	@Override
	public void getBeacons(ApiCallback<Beacon[]> callback) {
		if(callback == null) return;
		
		Beacon[] beacons = new Beacon[5];
		
		beacons[0] = newBeacon(1, "1.0", 0, 0);
		beacons[1] = newBeacon(2, "2.0", 0, 0);
		beacons[2] = newBeacon(3, "2.1", 0, 0);
		beacons[3] = newBeacon(4, "2.2", 0, 0);
		beacons[4] = newBeacon(5, "3.0", 0, 0);
		
		callback.result(beacons);		
	}
	
	private Beacon newBeacon(int id, String beaconId, int minor, int major) {
		Beacon beacon = new Beacon();
		
		beacon.setBeaconId(beaconId);
		beacon.setMinor(minor);
		beacon.setMajor(major);
		
		return beacon;
	}

	@Override
	public void getAdditions(ApiCallback<Addition[]> callback) {
		
		if(callback == null) return;
		
		Addition[] additions = new Addition[1];
		
		additions[0] = newAddition(1, 24, "youtube3", "https://www.youtube.com/watch?v=wZZ7oFKsKzY");
		
		callback.result(additions);
	}
	
	private Addition newAddition(int id, int itemId, String key, String value) {
		Addition addition = new Addition();
		
		addition.setId(id);
		addition.setItemId(itemId);
		addition.setKey(key);
		addition.setValue(value);
		
		return addition;
	}


}
