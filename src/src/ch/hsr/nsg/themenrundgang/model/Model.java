package ch.hsr.nsg.themenrundgang.model;

import java.util.Dictionary;

import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;

public class Model {

	private static DetailViewModel detailViewModel;
	private static NsgRepository repository;
	
	public static DetailViewModel getDetailViewModel() {
		if(detailViewModel == null)  {
			detailViewModel = new DetailViewModel(getRepository());
		}
		
		
	}
	
		
}
