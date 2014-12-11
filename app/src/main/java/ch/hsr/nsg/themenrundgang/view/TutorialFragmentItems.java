package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;

import ch.hsr.nsg.themenrundgang.R;

public class TutorialFragmentItems extends TutorialFragment {

    public static TutorialFragmentItems newInstance() {
        TutorialFragmentItems f = new TutorialFragmentItems();
        Bundle args = new Bundle();
        args.putInt(KEY_RESOURCE, R.layout.fragment_tutorial_items);
        f.setArguments(args);
        return f;
    }
}
