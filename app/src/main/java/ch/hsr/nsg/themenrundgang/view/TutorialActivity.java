package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toolbar;

import com.viewpagerindicator.LinePageIndicator;

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

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.indicator)
    LinePageIndicator indicator;
	
	TutorialPagerAdapter mPagerAdapter;

	NsgSyncTaskUi task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tutorial);
        mToolbar.inflateMenu(R.menu.menu_empty);

		mPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
		
		mPager.setPageTransformer(true, new DepthPageTransformer());
		mPager.setAdapter(mPagerAdapter);
        indicator.setViewPager(mPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                    case 3:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.green_dark));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                    case 1:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.red_dark));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.red));
                        break;
                    case 2:
                        getWindow().setStatusBarColor(getResources().getColor(R.color.blue_dark));
                        mToolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
		
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
                mPagerAdapter.setProgress(val);
			}
		}

		@Override
		protected void onPostExecute(Void result) {

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

        private final TutorialFragment[] tutorialFragments;

        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);

            tutorialFragments = new TutorialFragment[] {
                TutorialFragmentInfo.newInstance(),
                TutorialFragmentSubjects.newInstance(),
                TutorialFragmentItems.newInstance(),
                TutorialFragmentFinal.newInstance()
            };
		}

		@Override
		public Fragment getItem(int position) {

			return tutorialFragments[position];
		}

        public void setProgress(int value) {
            for(TutorialFragment frag : tutorialFragments) {
                frag.setProgress(value);
            }
        }

		@Override
		public int getCount() {
			return tutorialFragments.length;
		}
	}
}
