package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;

import ch.hsr.nsg.themenrundgang.R;

public class TutorialFragmentSubjects extends TutorialFragment {

    public static TutorialFragmentSubjects newInstance() {
        TutorialFragmentSubjects f = new TutorialFragmentSubjects();
        Bundle args = new Bundle();
        args.putInt(KEY_RESOURCE, R.layout.fragment_tutorial_subjects);
        f.setArguments(args);
        return f;
    }
}
