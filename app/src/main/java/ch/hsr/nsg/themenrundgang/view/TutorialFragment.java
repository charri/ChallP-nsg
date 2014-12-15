package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ch.hsr.nsg.themenrundgang.dagger.InjectingFragment;

public class TutorialFragment extends InjectingFragment {

	public static final String KEY_RESOURCE = "resource";

    public int getResource() {
        return getArguments().getInt(KEY_RESOURCE, 0);
    }

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		ViewGroup rootView = (ViewGroup)inflater.inflate(getResource(), container, false);
		
		ButterKnife.inject(this, rootView);

        onInjected();

		return rootView;
	}

    protected void onInjected() {

    }

    public void setProgress(int value) {

    }
}
