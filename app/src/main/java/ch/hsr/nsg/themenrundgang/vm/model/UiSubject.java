package ch.hsr.nsg.themenrundgang.vm.model;

import android.os.Parcel;
import android.os.Parcelable;

import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.utils.OnObservable;
import ch.hsr.nsg.themenrundgang.vm.SubjectViewModel;

public class UiSubject extends Subject implements Parcelable {

    public static UiSubject newInstance(Subject subject, String imageUrl) {
        UiSubject uiSubject = new UiSubject();
        uiSubject.setId(subject.getId());
        uiSubject.setDescription(subject.getDescription());
        uiSubject.setName(subject.getName());
        uiSubject.setParentId(subject.getParentId());
        uiSubject.setImageUrl(imageUrl);
        return uiSubject;
    }

    public UiSubject(Parcel in) {
        setId(in.readInt());
        setParentId(in.readInt());
        setName(in.readString());
        setDescription(in.readString());
        setImageUrl(in.readString());
    }

    public UiSubject() { }

    private OnObservable observable;

    public void setObservable(OnObservable observable) {
        this.observable = observable;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        if(this.isChecked == isChecked) return;

        this.isChecked = isChecked;
        if(observable != null) {
            observable.notifyOn(SubjectViewModel.KEY_SUBJECTS);
        }
    }

    private boolean isChecked = false;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<UiSubject> CREATOR
            = new Parcelable.Creator<UiSubject>() {
        public UiSubject createFromParcel(Parcel in) {
            return new UiSubject(in);
        }

        public UiSubject[] newArray(int size) {
            return new UiSubject[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeInt(getParentId());
        dest.writeString(getName());
        dest.writeString(getDescription());
        dest.writeString(getImageUrl());
    }
}
