package ch.hsr.nsg.themenrundgang;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import android.app.Activity;


public class InjectionHelper {
	private ObjectGraph activityGraph;
	
	public void onCreateImpl(InjectionProvider provider) {
		NsgApplication nsg = provider.getNsgApplication();
		activityGraph = nsg.getApplicationGraph().plus(getModulesImpl(provider).toArray());
		activityGraph.inject(provider.getActivity());
	}
	
	public void onDestoryImpl() {
		activityGraph = null;
	}
	
	public void injectImpl(Object object) {
		activityGraph.inject(object);
	}
	
	private List<Object> getModulesImpl(InjectionProvider provider) {
		return Arrays.<Object> asList(new ActivityModule(provider));
	}
	
	public void setContentViewImpl(Activity activity) {
		ButterKnife.inject(activity);
	}
	
}
