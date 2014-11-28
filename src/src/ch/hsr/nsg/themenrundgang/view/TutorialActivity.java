package ch.hsr.nsg.themenrundgang.view;

import javax.inject.Inject;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.applicationService.NsgSyncTask;
import ch.hsr.nsg.themenrundgang.model.Repositories;
import android.os.Bundle;

public class TutorialActivity extends BaseActivity {

	@Inject NsgApi api;
	@Inject Repositories repos;
	
	private NsgSyncTaskUi task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		task = new NsgSyncTaskUi();
		task.execute();
	}
	
	public class NsgSyncTaskUi extends NsgSyncTask {
		
		public NsgSyncTaskUi() {
			super(api, repos);
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO: 
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO: 
		}
	}
}
