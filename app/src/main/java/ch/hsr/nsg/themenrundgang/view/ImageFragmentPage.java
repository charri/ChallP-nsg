package ch.hsr.nsg.themenrundgang.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragment;


public class ImageFragmentPage extends InjectingFragment {

    public static final String KEY_IMAGE = "image";

    public String getImage() {return getArguments().getString(KEY_IMAGE, null);}


    @InjectView(R.id.image_view)
    ImageView mImageView;

    @Inject
    ImageLoader mImageLoader;

    private int mVibrantColor;
    private int mVibrantColorDark;
    private Callback mCallback;
    private ImageFragmentPage mSelf;

    public static ImageFragmentPage newInstance(String imageUrl) {
        ImageFragmentPage f = new ImageFragmentPage();
        Bundle args = new Bundle();
        args.putString(KEY_IMAGE, imageUrl);
        f.setArguments(args);
        return f;
    }


    public int getVibrantColor() {
        return mVibrantColor;
    }

    public int getVibrantColorDark() {
        return mVibrantColorDark;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
        mSelf = this;
    }

    @OnClick(R.id.content) void onSelect(View view) {
        if(mCallback == null) return;
        mCallback.onSelect(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_image_page, container, false);
        ButterKnife.inject(this, rootView);
        mImageLoader.displayImage(getImage(), mImageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Palette.generateAsync(((BitmapDrawable) mImageView.getDrawable()).getBitmap(), new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Drawable drawable = new ColorDrawable(palette.getVibrantColor(R.color.primary));
                        mVibrantColor = palette.getVibrantColor(R.color.primary);
                        mVibrantColorDark = palette.getDarkVibrantColor(R.color.primary_dark);
                        if(mCallback != null) mCallback.onColorThemeReady(mSelf);
                    }
                });

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return rootView;
    }

    public static interface Callback {
        void onColorThemeReady(ImageFragmentPage imageFragmentPage);
        void onSelect(ImageFragmentPage imageFragmentPage);
    }
}
