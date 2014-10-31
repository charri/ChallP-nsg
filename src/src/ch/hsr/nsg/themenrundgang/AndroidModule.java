package ch.hsr.nsg.themenrundgang;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;


/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(library = true)
public class AndroidModule {
  private final NsgApplication application;

  public AndroidModule(NsgApplication application) {
    this.application = application;
  }

  /**
   * Allow the application context to be injected but require that it be annotated with
   * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
   */
  @Provides @Singleton @ForApplication Context provideApplicationContext() {
    return application;
  }

  
}
