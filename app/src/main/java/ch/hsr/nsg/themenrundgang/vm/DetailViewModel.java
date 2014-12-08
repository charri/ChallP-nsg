package ch.hsr.nsg.themenrundgang.vm;

import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;

public class DetailViewModel {

	private final ItemRepository itemRepo;
	private final SubjectRepository subjectRepo;
	
	private Item item;
	
	public DetailViewModel(ItemRepository itemRepo, SubjectRepository subjectRepo) {
		this.itemRepo = itemRepo;
		this.subjectRepo = subjectRepo;
	}
	
	public Subject[] allSubjects() {
		return subjectRepo.allToplevelSubjects();
	}
	
	public String getHeaderLabelText() {
		return item.getName();
	}

	public Item getItem() {
		return item;
	}
	
	public void setItemById(int id) {
		item = itemRepo.itemById(id);
	}
	
}
