package ch.hsr.nsg.themenrundgang.model;

public class Item {
	
	
	private int id;
	private String name;
	private String description;
	private int[] subjects;
	private String[] beacons;
	private int[] images;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int[] getSubjects() {
		return subjects;
	}
	public void setSubjects(int[] subjects) {
		this.subjects = subjects;
	}
	public String[] getBeacons() {
		return beacons;
	}
	public void setBeacons(String[] beacons) {
		this.beacons = beacons;
	}
	public int[] getImages() {
		return images;
	}
	public void setImages(int[] images) {
		this.images = images;
	}
	
}
