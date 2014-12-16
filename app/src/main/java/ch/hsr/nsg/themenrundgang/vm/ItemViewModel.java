package ch.hsr.nsg.themenrundgang.vm;

import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.BeaconRepository;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.monitor.BeaconMonitor;
import ch.hsr.nsg.themenrundgang.monitor.MonitoringListenerCallback;
import ch.hsr.nsg.themenrundgang.monitor.Region;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;

public class ItemViewModel {

    private final NsgApi nsgApi;
    private final ItemRepository itemRepository;
    private final BeaconRepository beaconRepository;
    private final BeaconMonitor mBeaconMonitor;
    private Subject[] mSubjects;
    private ArrayList<Item> mItemsForSubjects;
    private final List<Region> mAllBeaconRegions;
    private ArrayList<UiItem> mItems;
    private UiItemListener mListener;

    public ItemViewModel(NsgApi nsgApi, ItemRepository itemRepository, BeaconRepository beaconRepository, BeaconMonitor beaconMonitor) {
        this.nsgApi = nsgApi;
        this.itemRepository = itemRepository;
        this.beaconRepository = beaconRepository;
        this.mBeaconMonitor = beaconMonitor;
        this.mAllBeaconRegions = new ArrayList<Region>();

        initializeAllBeaconRegions();
        try {
            startMonitoringAllRegions();
        } catch (RemoteException e) {

        }
    }

    public void setSubjects(Subject[] subjects) {
        mSubjects = subjects;
        setItemsForSubjects();
    }

    private void setItemsForSubjects() {
        mItemsForSubjects = new ArrayList<Item>(Arrays.asList(itemRepository.itemsForSubject(mSubjects)));
    }

    public void loadAllItems() {
        Item[] items = itemRepository.itemsForSubject(mSubjects);

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

    public void setUiItemListener(UiItemListener listener) {
        this.mListener = listener;
    }

    public void setBeaconMonitorListener() {
        mBeaconMonitor.setMonitoringListener(new MonitoringListenerCallback() {
            @Override
            public void onEnterRegion(Region region) {
                Beacon enteredBeacon = new Beacon(region.getMajor(), region.getMinor());
                Item[] beaconItems = itemRepository.itemsForBeacon(enteredBeacon);
                for (Item item : beaconItems) {
                    if (mItemsForSubjects.contains(item)) // TODO change to radom id
                        mListener.addItem(UiItem.newInstance(item, nsgApi.getImagePath(item.getId())));
                }
            }

            @Override
            public void onExitRegion(Region region) {
                //mAdapter.removeItem(null);
            }
        });
    }

    private void initializeAllBeaconRegions() {
        Beacon[] allBeacons = beaconRepository.allBeacons();
        for (Beacon beacon : allBeacons) {
            Integer locationId = beacon.getMajor();
            Integer beaconId = beacon.getMinor();
            String regionId = "region_beacon_" + locationId + "/" + beaconId;
            Region beaconRegion = new Region(regionId, locationId, beaconId);
            mAllBeaconRegions.add(beaconRegion);
        }
    }

    private void startMonitoringAllRegions() throws RemoteException {
        for (Region region : mAllBeaconRegions) {
            mBeaconMonitor.startMonitoring(region);
        }
    }

    public interface UiItemListener {
        void addItem(UiItem item);
        void removeItem(UiItem item);
    }
}