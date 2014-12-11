package ch.hsr.nsg.themenrundgang.vm;


import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.exceptions.ItemNotFoundException;

public class DetailViewModel {

	private final ItemRepository mItemRepo;

	private Item mItem;
    private NsgApi mApi;

	public DetailViewModel(NsgApi api, ItemRepository itemRepo) {
		mItemRepo = itemRepo;
        mApi = api;
	}
	
	public String getTitleText() {
		return mItem.getName();
	}

    public String getContentText() {
        return mItem.getDescription();
    }

	public Item getItem() {
		return mItem;
	}

	public void setItemById(int id) throws ItemNotFoundException, Exception {
        mItem = mItemRepo.itemById(id);
        if(mItem == null) throw new ItemNotFoundException("Item with Id " + id + " not found");
	}

    public int getImageLength() {
        if(mItem.getImages() == null) return 0;
        return mItem.getImages().length;
    }


    public String getImageUrl(int position) {
        if(!isValidImagePosition(position)) throw new IllegalArgumentException("Position out of bounds.");

        int imageId = mItem.getImages()[position];
        return mApi.getImagePath(imageId);
    }

    private boolean isValidImagePosition(int position) {
        return mItem.getImages() != null ||
               position >= 0 ||
               position < mItem.getImages().length;
    }

}
