package ch.hsr.nsg.themenrundgang.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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

    int progressValue = 1;

    @InjectView(R.id.progress)
    ProgressBar progressBar;

    @InjectView(R.id.btnStart) Button btnStart;


    @InjectView(R.id.container_info) View viewInfo;
    @InjectView(R.id.container_after) View viewAfter;

    @OnClick(R.id.btnStart)
    public void onClickStart(View view) {
        getActivity().startActivity(
                new Intent(getActivity(), SubjectsActivity.class),
                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle()
        );
    }

    @Override
    protected void onInjected() {
        updateUi();
    }

    @Override
    public void setProgress(int value) {
        progressValue = value;
        updateUi();
    }

    private void updateUi() {
        if(progressBar == null) return;
        progressBar.setProgress(progressValue);
        if(progressValue == progressBar.getMax()) {
            viewInfo.setVisibility(View.GONE);
            viewAfter.setVisibility(View.VISIBLE);
        }
    }
}
