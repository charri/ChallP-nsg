package ch.hsr.nsg.themenrundgang;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

import ch.hsr.nsg.themenrundgang.dagger.InjectingApplication;

public class NsgApplication extends InjectingApplication {
	
	@Override protected List<Object> getModules() {
		List<Object> modules = super.getModules();
		modules.add(new NsgApplicationModule());
		return modules;
	}

}
