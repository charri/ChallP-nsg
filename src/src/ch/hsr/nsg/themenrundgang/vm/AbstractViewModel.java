package ch.hsr.nsg.themenrundgang.vm;

import java.util.Arrays;
import java.util.List;

import ch.hsr.nsg.themenrundgang.NsgApplication;
import ch.hsr.nsg.themenrundgang.ViewModelModule;
import dagger.ObjectGraph;
import android.content.Context;

public class AbstractViewModel {

	private ObjectGraph activityGraph;
	
	public AbstractViewModel(Context context)
	{
		NsgApplication application = (NsgApplication) context.getApplicationContext();
		
		activityGraph = application.getApplicationGraph().plus(getModules().toArray());
		activityGraph.inject(this);
	}
	
	protected List<Object> getModules() {
		return Arrays.<Object> asList(new ViewModelModule());
	}
}
