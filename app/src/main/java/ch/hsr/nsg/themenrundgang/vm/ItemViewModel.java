package ch.hsr.nsg.themenrundgang.vm;

import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.monitor.BeaconMonitor;
import ch.hsr.nsg.themenrundgang.monitor.MonitoringListenerCallback;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;

public class ItemViewModel {

    private final NsgApi nsgApi;
    private final ItemRepository itemRepository;
    private final BeaconMonitor beaconMonitor;
    private Subject[] mSubjects;

    private ArrayList<UiItem> mItems;

    public ItemViewModel(NsgApi nsgApi, ItemRepository itemRepository, BeaconMonitor beaconMonitor) {
        this.nsgApi = nsgApi;
        this.itemRepository = itemRepository;
        this.beaconMonitor = beaconMonitor;
    }

    public void setSubjects(Subject[] subjects) {
        mSubjects = subjects;
    }

    public void loadAllItems() {
        Item[] items = itemRepository.itemsForSubject(mSubjects);

        mItems = new ArrayList<>(items.length);

        for(Item item : items) {
            String imageUrl = item.getId() == -1 ? null : nsgApi.getImagePath(item.getId());

            mItems.add(UiItem.newInstance(item, imageUrl));
        }
    }

    public ArrayList<UiItem> getItems() {
        return mItems;
    }


    public void setBeaconMonitorListener(MonitoringListenerCallback listener) {
        beaconMonitor.setMonitoringListener(listener);
    }

}