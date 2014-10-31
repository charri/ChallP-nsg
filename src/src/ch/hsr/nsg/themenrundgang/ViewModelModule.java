package ch.hsr.nsg.themenrundgang;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;
import dagger.Module;
import dagger.Provides;
import android.content.Context;

@Module(injects = { TestViewModel.class, DetailViewModel.class }, addsTo = AndroidModule.class, library = true)
public class ViewModelModule {

	@Inject Context context;

	public ViewModelModule() {
	}
		
	@Provides @Singleton ItemRepository provideItemRepository() {
		return new NsgRepository(context);
	}
}
