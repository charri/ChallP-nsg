package ch.hsr.nsg.themenrundgang;

import javax.inject.Singleton;

import android.content.Context;

import ch.hsr.nsg.themenrundgang.view.BaseActivity;
import ch.hsr.nsg.themenrundgang.view.DetailActivity;
import ch.hsr.nsg.themenrundgang.view.TestActivity;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module(injects = { TestActivity.class, DetailActivity.class }, addsTo = AndroidModule.class, library = true)
public class ActivityModule {
	private final BaseActivity activity;
	private final NsgApplication application;

	public ActivityModule(BaseActivity activity) {
		this.activity = activity;
		this.application = ((NsgApplication) activity.getApplication());
	}

	/**
	 * Allow the activity context to be injected but require that it be
	 * annotated with {@link ForActivity @ForActivity} to explicitly
	 * differentiate it from application context.
	 */
	@Provides
	@Singleton
	@ForActivity
	Context provideActivityContext() {
		return activity;
	}
	
	@Provides @Singleton TestViewModel provideTestViewModel() {
	  return new TestViewModel(application.getApplicationGraph());
  }
}
