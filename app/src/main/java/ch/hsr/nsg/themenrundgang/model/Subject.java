package ch.hsr.nsg.themenrundgang.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Subject implements Parcelable {
		
	private int id;
	private int parentId;
	
	private String name;
	private String description;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


    public Subject() {

    }

    public Subject(Parcel in) {
        int[] ids = new int[2];
        in.readIntArray(ids);
        id = ids[0]; parentId = ids[1];

        String[] strings = new String[2];
        name = strings[0]; description = strings[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Subject> CREATOR
            = new Parcelable.Creator<Subject>() {
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(new int[] { id, parentId });
        dest.writeStringArray(new String[] { name, description });
    }
}
