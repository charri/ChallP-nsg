package ch.hsr.nsg.themenrundgang.view;

import ch.hsr.nsg.themenrundgang.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ImagePageFragment extends android.support.v4.app.Fragment {
	
	private int position;
	
	public ImagePageFragment(int pos) {
		position = pos;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    	Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
        R.layout.fragment_image_page, container, false);
		
        return rootView;
    }
}
