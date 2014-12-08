package ch.hsr.nsg.themenrundgang.view;

import javax.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;
import android.widget.Toolbar;
import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;

public class DetailActivity extends BaseFragmentActivity {

	@Inject
	DetailViewModel viewModel;
	
	
	@InjectView(R.id.toolbar)
	Toolbar mToolbar;
	
	@InjectView(R.id.pager)
	ViewPager mPager;
	
	@InjectView(R.id.detail_image_caption)
	TextView mImageCaption;
	
	private PagerAdapter mPagerAdapter;
	private static final int NUM_PAGES = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_detail);
		setActionBar(mToolbar);
		mToolbar.setTitle("Braunbär");
		
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				updateImageCaption(arg0 + 1);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        updateImageCaption(1);
        
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
	
	private void updateImageCaption(int page) {
		mImageCaption.setText("Bild " + page + " von " + NUM_PAGES);
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.detail_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}*/
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return new ImagePageFragment(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
	
	
}
