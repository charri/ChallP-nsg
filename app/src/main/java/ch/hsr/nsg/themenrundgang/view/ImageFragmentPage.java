package ch.hsr.nsg.themenrundgang.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragment;


public class ImageFragmentPage extends InjectingFragment {

    public static final String KEY_IMAGE = "image";

    public String getImage() {return getArguments().getString(KEY_IMAGE, null);}


    @InjectView(R.id.imageView)
    ImageView imageView;

    @Inject
    ImageLoader mImageLoader;


    public static ImageFragmentPage newInstance(String imageUrl) {
        ImageFragmentPage f = new ImageFragmentPage();
        Bundle args = new Bundle();
        args.putString(KEY_IMAGE, imageUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_image_page, container, false);
        ButterKnife.inject(this, rootView);
        mImageLoader.displayImage(getImage(), imageView);
        return rootView;
    }
}
