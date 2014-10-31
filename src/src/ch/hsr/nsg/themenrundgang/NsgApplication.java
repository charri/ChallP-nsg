package ch.hsr.nsg.themenrundgang;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import android.app.Application;

public class NsgApplication extends Application {
	private ObjectGraph graph;

	@Override
	public void onCreate() {
		super.onCreate();

		graph = ObjectGraph.create(getModules().toArray());
	}

	protected List<Object> getModules() {
		return Arrays.<Object> asList(new AndroidModule(this));
	}

	public ObjectGraph getApplicationGraph() {
		return graph;
	}

	public void inject(Object object) {
		graph.inject(object);
	}
}
