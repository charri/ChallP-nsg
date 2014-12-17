package ch.hsr.nsg.themenrundgang.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivity;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragmentActivity;

public class DetailImageActivity extends InjectingActivity {

    private static final String EXTRA_IMAGE_URL = "imageUrl";
    public static final String EXTRA_IMAGE_BYTES = "byteArray";

    @InjectView(R.id.image_view)
    ImageView mImageView;

    @Inject
    ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image);

        if(getIntent().hasExtra(EXTRA_IMAGE_BYTES)) {
            ImageView previewThumbnail = new ImageView(this);
            byte[] bytes = getIntent().getByteArrayExtra(EXTRA_IMAGE_BYTES);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            mImageView.setImageBitmap(bitmap);
        }

       // mImageLoader.displayImage(getIntent().getStringExtra(EXTRA_IMAGE_URL), mImageView);

    }

    @OnClick(R.id.image_view) void onClick(View view) {
        finishAfterTransition();
    }



}