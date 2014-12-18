package ch.hsr.nsg.themenrundgang.vm;

import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.BeaconRepository;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.monitor.Region;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;

public class ItemViewModel {

    private final NsgApi nsgApi;
    private final ItemRepository itemRepository;
    private final BeaconRepository beaconRepository;
    private Subject[] mSubjects;
    private ArrayList<UiItem> mItems;

    public ItemViewModel(NsgApi nsgApi, ItemRepository itemRepository, BeaconRepository beaconRepository) {
        this.nsgApi = nsgApi;
        this.itemRepository = itemRepository;
        this.beaconRepository = beaconRepository;
    }

    public void setSubjects(Subject[] subjects) {
        mSubjects = subjects;
    }

    public void loadAllItems() {
        Item[] items = itemRepository.itemsFor(mSubjects);

        mItems = new ArrayList<UiItem>(items.length);

        for(Item item : items) {
            int randomImage = item.getRandomImage();
            String imageUrl = randomImage == -1 ? null : nsgApi.getImagePath(randomImage);

            mItems.add(UiItem.newInstance(item, imageUrl));
        }
    }

    public ArrayList<UiItem> getItems() {
        return mItems;
    }

    public ArrayList<Region> getRegionsForAllBeacons() {
        ArrayList<Region> allRegions = new ArrayList<Region>();
        Beacon[] allBeacons = beaconRepository.allBeacons();
        for (Beacon beacon : allBeacons) {
            Integer locationId = beacon.getMajor();
            Integer beaconId = beacon.getMinor();
            String regionId = "region_beacon_" + locationId + "/" + beaconId;
            Region beaconRegion = new Region(regionId, locationId, beaconId);
            allRegions.add(beaconRegion);
        }
        return allRegions;
    }

    public Item[] getItemsForSelectedSubjectsAndBeacon(int major, int minor) {
        Beacon beacon = new Beacon(major, minor); // beacon id can be initialised by major, minor
        return itemRepository.itemsFor(beacon, mSubjects);
    }

    public UiItem[] getUiItemsFrom(Region region) {
        Beacon exitedBeacon = new Beacon(region.getMajor(), region.getMinor());
        Item[] beaconItems = itemRepository.itemsFor(exitedBeacon, mSubjects);
        UiItem[] uiItems = new UiItem[beaconItems.length];
        for (int i = 0; i < beaconItems.length; i++) {

            int randomImage = beaconItems[i].getRandomImage();
            String imageUrl = randomImage == -1 ? null : nsgApi.getImagePath(randomImage);
            UiItem uiItem = UiItem.newInstance(beaconItems[i], imageUrl);

            uiItems[i] = uiItem;
        }
        return uiItems;
    }
}
