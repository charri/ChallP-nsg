package ch.hsr.nsg.themenrundgang.model;

import ch.hsr.nsg.themenrundgang.monitor.Region;

public class Beacon {
	private String name;
	private String proximityUUID;
	private int major;
	private int minor;
	
	public Beacon() { }
	
	public Beacon(String name, int major, int minor) {
		this.name = name;
		this.setProximityUUID(Region.UUID);
		this.major = major;
		this.minor = minor;
	}
	
	public String getBeaconId() {
		return name;
	}

	public void setBeaconId(String beaconId) {
		this.name = beaconId;
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
