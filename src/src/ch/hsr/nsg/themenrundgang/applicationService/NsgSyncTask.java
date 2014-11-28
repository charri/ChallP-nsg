package ch.hsr.nsg.themenrundgang.applicationService;

import android.os.AsyncTask;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Repositories;
import ch.hsr.nsg.themenrundgang.model.Subject;

public class NsgSyncTask extends AsyncTask<Void, Integer, Void> {

	private final NsgApi api;
	private final Repositories repos;
	
	private static final int STEPS = 5;
	private int step = 0;
	
	public NsgSyncTask(NsgApi api, Repositories repos) {
		this.api = api;
		this.repos = repos;
	}
		
	@Override
	protected Void doInBackground(Void... params) {
		
		for ( Subject s : api.getSubjects()) {
			repos.insertOrUpdate(s);
		}
		finishStep();
		
		for (Beacon b : api.getBeacons()) {
			repos.insertOrUpdate(b);
		}
		finishStep();
		
		for(Item i : api.getItems()) {
			repos.insertOrUpdate(i);
		}
		finishStep();
		
		for(Addition a : api.getAdditions()) {
			repos.insertOrUpdate(a);
		}
		finishStep();
		
		return null;
	}
	
	private void finishStep() {
		publishProgress(++step / STEPS * 100);
	}
	
}
