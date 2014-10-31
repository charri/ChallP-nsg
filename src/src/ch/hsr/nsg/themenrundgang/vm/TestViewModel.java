package ch.hsr.nsg.themenrundgang.vm;

import javax.inject.Inject;

import android.content.Context;

import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class TestViewModel extends AbstractViewModel {

	@Inject
	ItemRepository repository;
	
	public TestViewModel(Context context)
	{
		super(context);
	}
	
	public ItemRepository getRepository() {
		return repository;
	}
}
