package ch.hsr.nsg.themenrundgang.view;

import javax.inject.Inject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;
import android.widget.Toolbar;
import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.applicationService.NsgApiServiceFake;
import ch.hsr.nsg.themenrundgang.db.NsgRepository;
import ch.hsr.nsg.themenrundgang.model.Addition;
import ch.hsr.nsg.themenrundgang.model.Beacon;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;

public class DetailActivity extends BaseActivity {

	@Inject
	DetailViewModel viewModel;
	
	
	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail_view);
		/*try {
			viewModel.setItemById(getIntent().getExtras().getInt("itemId"));
			setContentView(R.layout.detail_view);
			if(mToolbar != null) {
				setActionBar(mToolbar);
				mToolbar.setTitle(viewModel.getItem().getName());
			}
		}
		catch(Exception e) {
			Toast.makeText(getApplicationContext(), R.string.detail_activity_missing_parameter_msg, Toast.LENGTH_SHORT).show();
			finish();
		}*/
		
		
		
	};
	
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.detail_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/
	
	
	
}
