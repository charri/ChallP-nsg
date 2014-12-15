package ch.hsr.nsg.themenrundgang.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;
import butterknife.InjectView;
import butterknife.OnPageChange;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragmentActivity;
import ch.hsr.nsg.themenrundgang.exceptions.ItemNotFoundException;
import ch.hsr.nsg.themenrundgang.model.Item;
import ch.hsr.nsg.themenrundgang.utils.BitmapUtils;
import ch.hsr.nsg.themenrundgang.utils.ThemeHelper;
import ch.hsr.nsg.themenrundgang.vm.DetailViewModel;

public class DetailActivity extends InjectingFragmentActivity implements ImageFragmentPage.Callback, ThemeHelper.Themable {

    private static final String EXTRA_ITEM = "itemId";
    private static final int ITEM_ID_DEBUG = 2;

    private ImageFragmentPage[] mFragmentCache = new ImageFragmentPage[]{};


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

    public static Intent getIntent(Context context, Item item) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ITEM, item.getId());
        return intent;
    }


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
            mFragmentCache = new ImageFragmentPage[mViewModel.getImageLength()];
        }
    }

    private Fragment getCachedFragment(int position) {
        setupFragmentCache();
        if(mFragmentCache[position] == null) {
            mFragmentCache[position] = ImageFragmentPage.newInstance(mViewModel.getImageUrl(position));
            mFragmentCache[position].setCallback(this);
        }
        return mFragmentCache[position];
    }

    private void setupText() {
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
        mImageCaption.setText(
                getString(R.string.image_caption_image) +
                page + " " +
                getString(R.string.image_caption_of)+" " +
                mViewModel.getImageLength()
        );
    }



    @OnPageChange(R.id.pager) void onPageChanged(int position) {
        setImageCaption(position + 1);
        updateColorTheme(mFragmentCache[position]);
    }

    @Override
    public void onColorThemeReady(ImageFragmentPage imageFragmentPage) {
        updateColorTheme(mFragmentCache[mPager.getCurrentItem()]);
    }

    @Override
    public void onSelect(ImageFragmentPage imageFragmentPage) {
        ImageView imageView = imageFragmentPage.getImageView();
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        Intent intent = new Intent(this, DetailImageActivity.class);
        intent.putExtra(DetailImageActivity.EXTRA_IMAGE_BYTES, BitmapUtils.getBytes(bitmap));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageFragmentPage.getImageView(), getString(R.string.transition_detail_image));
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }


    private void updateColorTheme(ImageFragmentPage imageFragmentPage) {
        ThemeHelper.changeColorTheme(this,imageFragmentPage.getVibrantColorDark(), imageFragmentPage.getVibrantColor());
    }

    @Override
    public View getWidget() {
        return mToolbar;
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
