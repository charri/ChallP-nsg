package ch.hsr.nsg.themenrundgang.vm;

import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.monitor.BeaconMonitor;

public class ItemViewModel {

    private final BeaconMonitor mMonitor;
    private final ItemRepository mItemRepository;
    private final ArrayList<UiItem> mItems = new ArrayList<UiItem>();

    public ItemViewModel(ItemRepository itemRepository, BeaconMonitor monitor) {
        mItemRepository = itemRepository;
        mMonitor = monitor;

        startMonitoringRegions();
    }

    private void startMonitoringRegions() {

    }

    public ArrayList<UiItem> getItems() {
        return mItems;
    }

    public class UiItem {

    }
}