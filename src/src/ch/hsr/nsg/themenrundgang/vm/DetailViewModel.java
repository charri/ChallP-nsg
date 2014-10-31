package ch.hsr.nsg.themenrundgang.vm;

import javax.inject.Inject;

import android.content.Context;

import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class DetailViewModel extends AbstractViewModel {

	
	@Inject ItemRepository repo;
	
	public DetailViewModel(Context context) {
		super(context);
	}
	
}
