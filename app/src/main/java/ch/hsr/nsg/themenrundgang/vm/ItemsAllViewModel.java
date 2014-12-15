package ch.hsr.nsg.themenrundgang.vm;


import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.vm.model.UiItem;

public class ItemsAllViewModel extends AbstractViewModel {

    private final NsgApi nsgApi;
    private final ItemRepository itemRepository;
    private Subject[] mSubjects;

    private ArrayList<UiItem> mItems;

    public ItemsAllViewModel(NsgApi nsgApi, ItemRepository itemRepository) {
        this.nsgApi = nsgApi;
        this.itemRepository = itemRepository;
    }

    public void setSubjects(Subject[] subjects) {
        mSubjects = subjects;

        Item[] items = itemRepository.itemsForSubject(null, subjects);

        mItems = new ArrayList<>(items.length);

        for(Item item : items) {
            String imageUrl = item.getId() == -1 ? null : nsgApi.getImagePath(item.getId());

            mItems.add(UiItem.newInstance(item, imageUrl));
        }
    }

    public ArrayList<UiItem> getItems() {
        return mItems;
    }
}
