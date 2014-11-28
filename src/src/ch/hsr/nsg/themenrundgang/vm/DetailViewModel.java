package ch.hsr.nsg.themenrundgang.vm;

import javax.inject.Inject;

import android.content.Context;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import dagger.ObjectGraph;

public class DetailViewModel extends AbstractViewModel {

	
	@Inject ItemRepository itemRepo;
	@Inject SubjectRepository subjectRepo;
	
	private Item item;
	
	public DetailViewModel(Context context, ObjectGraph graph) {
		super(context, graph);
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
