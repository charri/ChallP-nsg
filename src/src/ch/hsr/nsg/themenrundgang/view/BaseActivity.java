package ch.hsr.nsg.themenrundgang.view;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

import ch.hsr.nsg.themenrundgang.ActivityModule;
import ch.hsr.nsg.themenrundgang.NsgApplication;
import dagger.ObjectGraph;
import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	private ObjectGraph activityGraph;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Perform injection so that when this call returns all dependencies
		// will be available for use.
		NsgApplication nsg = (NsgApplication) getApplication();

		activityGraph = nsg.getApplicationGraph().plus(getModules().toArray());
		
		activityGraph.inject(this);
		

	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ButterKnife.inject(this);
	}

	@Override
	protected void onDestroy() {
		// Eagerly clear the reference to the activity graph to allow it to be
		// garbage collected as
		// soon as possible.
		activityGraph = null;

		super.onDestroy();
	}

	protected List<Object> getModules() {
		return Arrays.<Object> asList(new ActivityModule(this));
	}

	public void inject(Object object) {
		activityGraph.inject(object);
	}
}
