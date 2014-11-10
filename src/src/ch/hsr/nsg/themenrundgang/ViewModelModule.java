package ch.hsr.nsg.themenrundgang;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.model.AdditionRepository;
import ch.hsr.nsg.themenrundgang.model.BeaconRepository;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;
import dagger.Module;
import dagger.Provides;
import android.content.Context;

@Module(injects = { TestViewModel.class, DetailViewModel.class }, addsTo = AndroidModule.class, library = true)
public class ViewModelModule {

	@Inject Context context;

	NsgRepository repository;
	
	public ViewModelModule() {
		repository = new NsgRepository(context);
	}
			
	@Provides @Singleton ItemRepository provideItemRepository() {
		return repository;
	}
	
	@Provides @Singleton AdditionRepository provideAdditionRepository() {
		return repository;
	}
	
	@Provides @Singleton BeaconRepository provideBeaconRepository() {
		return repository;
	}
	
	@Provides @Singleton SubjectRepository provideSubjectRepository() {
		return repository;
	}
}
