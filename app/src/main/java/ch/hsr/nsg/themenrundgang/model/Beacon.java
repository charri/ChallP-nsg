package ch.hsr.nsg.themenrundgang.model;

import ch.hsr.nsg.themenrundgang.monitor.Region;

public class Beacon {
	
	private String beaconId;

    private String proximityUUID = Region.UUID;
	private int major;
	private int minor;

    public Beacon() {}

    public Beacon(String beaconId, int major, int minor) {
        this.beaconId = beaconId;
        this.major = major;
        this.minor = minor;
    }
	
	public String getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

    public String getProximityUUID() {
        return proximityUUID;
    }

    public void setProximityUUID(String proximityUUID) {
        this.proximityUUID = proximityUUID;
    }

    @Override
    public boolean equals(Object other) {
        if (getClass() == other.getClass()) {
            return (this.major == ((Beacon) other).major
                    && this.minor == ((Beacon) other).minor);
        }
        return false;
    }
}
