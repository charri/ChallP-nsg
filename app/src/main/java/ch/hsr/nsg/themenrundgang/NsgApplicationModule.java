package ch.hsr.nsg.themenrundgang;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import javax.inject.Singleton;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceFake;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceHttp;
import ch.hsr.nsg.themenrundgang.dagger.InjectingApplication.InjectingApplicationModule;
import ch.hsr.nsg.themenrundgang.dagger.InjectingApplication.InjectingApplicationModule.Application;
import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.model.AdditionRepository;
import ch.hsr.nsg.themenrundgang.model.BeaconRepository;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;
import ch.hsr.nsg.themenrundgang.model.Repositories;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require a {@link android.content.Context} or
 * {@link android.app.Application} to create.
 */
@Module(
		injects = { NsgApplication.class },
		addsTo = InjectingApplicationModule.class,
		library = true
)
public class NsgApplicationModule {
	
	@Provides @Singleton
    NsgApi provideNsgApi() {
		return new NsgApiServiceHttp();
	}
	
	@Provides @Singleton
    Repositories provideRepositories(@Application Context context) {
		return new NsgRepository(context);
	}

	@Provides @Singleton
    ItemRepository provideItemRepository(@Application Context context) {
		return provideRepositories(context);
	}

	@Provides @Singleton
    AdditionRepository provideAdditionRepository(@Application Context context) {
		return provideRepositories(context);
	}

	@Provides @Singleton BeaconRepository provideBeaconRepository(@Application Context context) {
		return provideRepositories(context);
	}

	@Provides @Singleton
    SubjectRepository provideSubjectRepository(@Application Context context) {
		return provideRepositories(context);
	}

    @Provides @Singleton
    ImageLoader provideImageLoader(@Application Context context) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_loading) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_loading) // resource or drawable
                .resetViewBeforeLoading(true)  // default
                .delayBeforeLoading(0)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        ImageLoaderConfiguration config =  new ImageLoaderConfiguration.Builder(context)
                .writeDebugLogs()
                .defaultDisplayImageOptions(options)
                .build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        return imageLoader;
    }
}
