package ch.hsr.nsg.themenrundgang.model;

import ch.hsr.nsg.themenrundgang.monitor.Region;

public class Beacon {
	
	private String beaconId;

    private String proximityUUID = Region.UUID;
	private int major;
	private int minor;

    public Beacon() {}

    public Beacon(int major, int minor) {
        this.major = major;
        this.minor = minor;
        this.beaconId = composeBeaconId();
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

    private String composeBeaconId() {
        return proximityUUID + "/" + major + "/" + minor;
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
