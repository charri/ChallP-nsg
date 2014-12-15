package ch.hsr.nsg.themenrundgang.view;


import android.os.Bundle;

import ch.hsr.nsg.themenrundgang.dagger.InjectingFragment;
import ch.hsr.nsg.themenrundgang.vm.model.UiSubject;

public class ItemsFragmentBeacons extends InjectingFragment {

    private final static String EXTRA_SUBJECTS = ItemsFragmentBeacons.class.getName() + ":subjects";

    public static ItemsFragmentBeacons newInstance(UiSubject[] subjects) {
        ItemsFragmentBeacons f = new ItemsFragmentBeacons();
        Bundle args = new Bundle();
        args.putParcelableArray(EXTRA_SUBJECTS, subjects);
        f.setArguments(args);
        return f;
    }

}
