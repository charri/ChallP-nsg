package ch.hsr.nsg.themenrundgang;

import java.util.List;

import ch.hsr.nsg.themenrundgang.dagger.InjectingApplication;

public class NsgApplication extends InjectingApplication {
	
	@Override protected List<Object> getModules() {
		List<Object> modules = super.getModules();
		modules.add(new NsgApplicationModule());
		return modules;
	}

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
