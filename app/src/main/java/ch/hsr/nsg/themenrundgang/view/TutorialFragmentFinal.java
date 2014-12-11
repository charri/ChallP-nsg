package ch.hsr.nsg.themenrundgang.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.InjectView;
import butterknife.OnClick;
import ch.hsr.nsg.themenrundgang.R;


public class TutorialFragmentFinal extends TutorialFragment {

    public static TutorialFragmentFinal newInstance() {
        TutorialFragmentFinal f = new TutorialFragmentFinal();
        Bundle args = new Bundle();
        args.putInt(KEY_RESOURCE, R.layout.fragment_tutorial_final);
        f.setArguments(args);
        return f;
    }

    @InjectView(R.id.btnNext) Button btnNext;

    @OnClick(R.id.btnNext)
    public void onClickNext(View view) {
        startActivity(new Intent(getActivity(), SubjectsActivity.class));
    }

}
