package ch.hsr.nsg.themenrundgang.vm;

import javax.inject.Inject;

import android.content.Context;

import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import dagger.ObjectGraph;

public class TestViewModel extends AbstractViewModel {

	@Inject
	ItemRepository repository;
	
	public TestViewModel(ObjectGraph graph) {
		super(graph);
	}
	
	public ItemRepository getRepository() {
		return repository;
	}
}
