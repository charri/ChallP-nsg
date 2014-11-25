package ch.hsr.nsg.themenrundgang.monitor;

import ch.hsr.nsg.themenrundgang.model.Beacon;

public class Region {
	public final static String UUID = "nsg";
	
	private String identifier;
	private String proximityUUID;
	private Integer major;
	private Integer minor;
	
	public Region(String identifier, Integer major, Integer minor) {
		this.setIdentifier(identifier);
		this.setProximityUUID(UUID);
		this.setMajor(major);
		this.setMinor(minor);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getProximityUUID() {
		return proximityUUID;
	}

	public void setProximityUUID(String proximityUUID) {
		this.proximityUUID = proximityUUID;
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getMinor() {
		return minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}
	
	public boolean contains(Beacon beacon) {
		if (minor == null || minor == beacon.getMinor()) {
			if (major == null || major == beacon.getMajor()) {
				if (proximityUUID == null || proximityUUID.equals(beacon.getProximityUUID())) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object other) {
		if (getClass() == other.getClass()) {
			return (this.getIdentifier().equals(((Region) other).getIdentifier())
					&& this.getProximityUUID().equals(((Region) other).getProximityUUID())
					&& this.getMajor() == ((Region) other).getMajor()
					&& this.getMinor() == ((Region) other).getMinor());
		}
		return false;
	}
	
	@Override
	public String toString() {
		if (this.minor != null) {
			return "Beacon: " + this.major + "." + this.minor;
		} else if (this.major != null) {
			return "Location: " + this.major;
		} else if ((this.proximityUUID != null) && !this.proximityUUID.equals("")) {
			return "Area: " + this.proximityUUID;
		} else {
			return identifier;
		}
	}
}
