package ch.hsr.nsg.themenrundgang.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;


public class TutorialFragmentFinal extends TutorialFragment {
	
	@InjectView(R.id.btnNext) Button btnNext;

    public static TutorialFragmentFinal newInstance() {
        TutorialFragmentFinal f = new TutorialFragmentFinal();
        Bundle args = new Bundle();
        args.putInt(KEY_RESOURCE, R.layout.fragment_tutorial_final);
        f.setArguments(args);
        return f;
    }

	
	@Override protected void onInjected(Bundle savedInstanceState) {
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				startActivity(new Intent(getActivity(), SubjectsActivity.class));
			}
		});
	}
}
