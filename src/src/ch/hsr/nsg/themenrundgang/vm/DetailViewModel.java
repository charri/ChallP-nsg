package ch.hsr.nsg.themenrundgang.vm;

import javax.inject.Inject;


import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import dagger.ObjectGraph;

public class DetailViewModel extends AbstractViewModel {

	
	@Inject ItemRepository repo;
	
	public DetailViewModel(ObjectGraph graph) {
		super(graph);
	}
	
}
