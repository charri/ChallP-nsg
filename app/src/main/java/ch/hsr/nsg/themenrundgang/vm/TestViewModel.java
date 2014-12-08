package ch.hsr.nsg.themenrundgang.vm;

import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class TestViewModel {

	private final ItemRepository repository;
	
	public TestViewModel(ItemRepository repository) {
		this.repository = repository;
	}
	
	public ItemRepository getRepository() {
		return repository;
	}
}
