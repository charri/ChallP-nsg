package ch.hsr.nsg.themenrundgang.vm.model;

import android.os.Parcel;
import android.os.Parcelable;

import ch.hsr.nsg.themenrundgang.model.Item;

public class UiItem extends Item implements Parcelable {

    public static UiItem newInstance(Item item, String imageUrl) {
        UiItem uiItem = new UiItem();
        uiItem.setId(item.getId());
        uiItem.setDescription(item.getDescription());
        uiItem.setName(item.getName());
        uiItem.setImageUrl(imageUrl);
        uiItem.setImages(item.getImages());
        uiItem.setBeacons(item.getBeacons());
        uiItem.setSubjects(item.getSubjects());
        return uiItem;
    }

    public UiItem() {

    }

    public UiItem(Parcel in) {
        setId(in.readInt());
        setName(in.readString());
        setDescription(in.readString());
        setImageUrl(in.readString());
        setSubjects(in.createIntArray());
        setBeacons(in.createStringArray());
        setImages(in.createIntArray());
    }

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static final Parcelable.Creator<UiItem> CREATOR
            = new Parcelable.Creator<UiItem>() {
        public UiItem createFromParcel(Parcel in) {
            return new UiItem(in);
        }

        public UiItem[] newArray(int size) {
            return new UiItem[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getImageUrl());
        dest.writeIntArray(getSubjects());
        dest.writeStringArray(getBeacons());
        dest.writeIntArray(getImages());
    }
}