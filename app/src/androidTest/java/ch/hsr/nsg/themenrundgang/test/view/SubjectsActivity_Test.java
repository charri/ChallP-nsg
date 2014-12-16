package ch.hsr.nsg.themenrundgang.test.view;


import android.test.ActivityInstrumentationTestCase2;

import ch.hsr.nsg.themenrundgang.view.SubjectsActivity;

public class SubjectsActivity_Test extends ActivityInstrumentationTestCase2 {

    private SubjectsActivity mActivity;


    public SubjectsActivity_Test() {
        super(SubjectsActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        mActivity = (SubjectsActivity)getActivity();

    }

    public void testClick() {

    }

}

