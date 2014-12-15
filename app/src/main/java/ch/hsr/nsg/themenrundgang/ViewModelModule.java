package ch.hsr.nsg.themenrundgang;

import javax.inject.Singleton;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Repositories;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.monitor.BeaconMonitor;
import ch.hsr.nsg.themenrundgang.monitor.BeaconService;
import ch.hsr.nsg.themenrundgang.view.DetailActivity;
import ch.hsr.nsg.themenrundgang.view.DetailImageActivity;
import ch.hsr.nsg.themenrundgang.view.ImageFragmentPage;
import ch.hsr.nsg.themenrundgang.view.ItemsActivity;
import ch.hsr.nsg.themenrundgang.view.ItemsFragmentAll;
import ch.hsr.nsg.themenrundgang.view.ItemsFragmentBeacons;
import ch.hsr.nsg.themenrundgang.view.SplashActivity;
import ch.hsr.nsg.themenrundgang.view.SubjectsActivity;
import ch.hsr.nsg.themenrundgang.view.TestActivity;
import ch.hsr.nsg.themenrundgang.view.TutorialActivity;
import ch.hsr.nsg.themenrundgang.view.TutorialFragment;
import ch.hsr.nsg.themenrundgang.view.TutorialFragmentFinal;
import ch.hsr.nsg.themenrundgang.view.TutorialFragmentInfo;
import ch.hsr.nsg.themenrundgang.view.TutorialFragmentItems;
import ch.hsr.nsg.themenrundgang.view.TutorialFragmentSubjects;
import ch.hsr.nsg.themenrundgang.view.adapter.ItemAdapter;
import ch.hsr.nsg.themenrundgang.view.adapter.SubjectAdapter;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;
import ch.hsr.nsg.themenrundgang.vm.ItemViewModel;
import ch.hsr.nsg.themenrundgang.vm.SubjectViewModel;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;
import ch.hsr.nsg.themenrundgang.vm.TutorialViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module(
	injects = { 
			DetailActivity.class, TestActivity.class, TutorialActivity.class, TutorialFragment.class, SplashActivity.class,
			TutorialFragmentInfo.class, TutorialFragmentFinal.class, TutorialFragmentItems.class, TutorialFragmentSubjects.class,
            SubjectsActivity.class, SubjectAdapter.class, ItemsActivity.class, ItemAdapter.class,
            BeaconService.class, ItemsFragmentAll.class, ItemsFragmentBeacons.class, ImageFragmentPage.class,
            DetailImageActivity.class
	},
	addsTo = NsgApplicationModule.class,
	library = true
)
public class ViewModelModule {
		
	@Provides @Singleton TestViewModel provideTestViewModel(ItemRepository itemRepo) {
	  return new TestViewModel(itemRepo);
	}
	
	@Provides @Singleton DetailViewModel provideDetailViewModel(NsgApi api, ItemRepository itemRepo) {
		return new DetailViewModel(api, itemRepo);
	}
	
	@Provides @Singleton TutorialViewModel provideTutorialViewModel(NsgApi nsgApi, Repositories repos) {
		return new TutorialViewModel(nsgApi, repos);
	}

    @Provides @Singleton
    SubjectViewModel provideSubjectViewModel(NsgApi nsgApi, SubjectRepository subjectRepo) {
        return new SubjectViewModel(nsgApi, subjectRepo);
    }

    @Provides @Singleton
    ItemViewModel provideItemViewModel(NsgApi nsgApi, ItemRepository itemRepo, BeaconMonitor beaconMonitor) {
        return new ItemViewModel(nsgApi, itemRepo, beaconMonitor);
    }

}
