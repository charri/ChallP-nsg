package ch.hsr.nsg.themenrundgang.test.view;

import ch.hsr.nsg.themenrundgang.test.TestModule;
import dagger.Module;

public class DetailView_Test {

	@Module(injects = DetailView_Test.class, includes = TestModule.class)
	static class MyTestModule {
		
	}
	
	
}
