package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import javax.inject.Inject;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.animations.DepthPageTransformer;
import ch.hsr.nsg.themenrundgang.applicationService.NsgSyncTask;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragmentActivity;
import ch.hsr.nsg.themenrundgang.vm.TutorialViewModel;

public class TutorialActivity extends InjectingFragmentActivity {

	@Inject
	TutorialViewModel viewModel;

	@InjectView(R.id.pager)
	ViewPager mPager;
	
	private PagerAdapter mPagerAdapter;

	private NsgSyncTaskUi task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tutorial);

		mPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
		
		mPager.setPageTransformer(true, new DepthPageTransformer());
		mPager.setAdapter(mPagerAdapter); 
			
		
		task = new NsgSyncTaskUi();
		task.execute();
	}

	public class NsgSyncTaskUi extends NsgSyncTask {

		public NsgSyncTaskUi() {
			super(viewModel.getApi(), viewModel.getRepositories());
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			for(Integer val : values) {
				System.out.println("Values :" + val);
			}
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO:
		}
	}

	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	private class TutorialPagerAdapter extends FragmentStatePagerAdapter {
		public TutorialPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position) {
			case 0: return TutorialFragmentInfo.newInstance();
			case 1: return TutorialFragmentSubjects.newInstance();
			case 2: return TutorialFragmentItems.newInstance();
			case 3: return TutorialFragmentFinal.newInstance();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 4;
		}
	}
}
