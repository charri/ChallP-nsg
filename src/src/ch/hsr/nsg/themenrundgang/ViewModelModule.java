package ch.hsr.nsg.themenrundgang;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.AdditionRepository;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.BeaconRepository;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import android.content.Context;

@Module(injects = { TestViewModel.class, DetailViewModel.class }, addsTo = AndroidModule.class, library = true)
public class ViewModelModule {

	NsgRepository repo;
	
	public ViewModelModule(Context context) {

		repo = new NsgRepository(context);
	}

			
	@Provides @Singleton ItemRepository provideItemRepository() {
		return repo;
	}
	
	@Provides @Singleton AdditionRepository provideAdditionRepository() {
		return repo;
	}
	
	@Provides @Singleton BeaconRepository provideBeaconRepository() {
		return repo;
	}
	
	@Provides @Singleton SubjectRepository provideSubjectRepository() {
		return repo;
	}
}
