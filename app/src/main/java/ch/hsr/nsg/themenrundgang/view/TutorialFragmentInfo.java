package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;

import ch.hsr.nsg.themenrundgang.R;

public class TutorialFragmentInfo extends TutorialFragment {

    public static TutorialFragmentInfo newInstance() {
        TutorialFragmentInfo f = new TutorialFragmentInfo();
        Bundle args = new Bundle();
        args.putInt(KEY_RESOURCE, R.layout.fragment_tutorial_info);
        f.setArguments(args);
        return f;
    }


	public TutorialFragmentInfo() {
	}
}
