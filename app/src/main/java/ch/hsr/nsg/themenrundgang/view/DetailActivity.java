package ch.hsr.nsg.themenrundgang.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import javax.inject.Inject;
import butterknife.InjectView;
import butterknife.OnPageChange;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragmentActivity;
import ch.hsr.nsg.themenrundgang.exceptions.ItemNotFoundException;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;

public class DetailActivity extends InjectingFragmentActivity {

    private static final String EXTRA_ITEM = "itemId";
    private static final int ITEM_ID_DEBUG = 2;

    private Fragment[] mFragmentCache = new Fragment[]{};

    @Inject
    DetailViewModel mViewModel;
    private PagerAdapter mPagerAdapter;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.pager)
    ViewPager mPager;

    @InjectView(R.id.image_caption)
    TextView mImageCaption;

    @InjectView(R.id.title)
    TextView mTitle;

    @InjectView(R.id.content)
    TextView mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!setupViewModel()) return;
        setupUi();
    }

    private boolean setupViewModel() {
        int itemId = findItemId(getIntent());

        try {
            mViewModel.setItemById(itemId);
            return true;
        } catch(ItemNotFoundException e) {
            Toast.makeText(getApplicationContext(), R.string.detail_activity_item_not_found_msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.detail_activity_unknown_error_msg, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void setupUi() {
        setContentView(R.layout.activity_detail);
        setupText();
        setupViewPager();
        setupImageCaption();
    }

    private void setupViewPager() {
        if(mViewModel.getImageLength() == 0) {
            ((ViewManager)mPager.getParent()).removeView(mPager);
            return;
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private void setupFragmentCache() {
        if(mFragmentCache == null ||
           mFragmentCache.length < mViewModel.getImageLength()) {
            mFragmentCache = new Fragment[mViewModel.getImageLength()];
        }
    }

    private Fragment getCachedFragment(int position) {
        setupFragmentCache();
        if(mFragmentCache[position] == null) {
            mFragmentCache[position] = ImageFragmentPage.newInstance(mViewModel.getImageUrl(position));
        }
        return mFragmentCache[position];
    }

    private void setupText() {
        setActionBar(mToolbar);
        mToolbar.setTitle(mViewModel.getTitleText());
        mTitle.setText(mViewModel.getTitleText());
        mContent.setText(mViewModel.getContentText());
    }

    private void setupImageCaption() {
        if(mViewModel.getImageLength() <= 1) {
            ((ViewManager)mImageCaption.getParent()).removeView(mImageCaption);
            return;
        }
        setImageCaption(1);
    }
    private int findItemId(Intent intent) {
        if(!hasRequiredExtras(intent)) return ITEM_ID_DEBUG;
        return getIntent().getExtras().getInt(EXTRA_ITEM);
    }


    private boolean hasRequiredExtras(Intent intent) {
        if(!intent.hasExtra(EXTRA_ITEM)) return false;
        return true;
    }

    private void setImageCaption(int page) {
        mImageCaption.setText("Bild " + page + " von " + mViewModel.getImageLength());
    }



    @OnPageChange(R.id.pager) void onPageChanged(int position) {
        setImageCaption(position + 1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCachedFragment(position);
        }

        @Override
        public int getCount() {
            return mViewModel.getImageLength();
        }
    }

}
