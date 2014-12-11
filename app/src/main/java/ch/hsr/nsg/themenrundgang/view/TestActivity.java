package ch.hsr.nsg.themenrundgang.view;

import android.os.Bundle;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.InjectView;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.dagger.InjectingActivity;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;

public class TestActivity extends InjectingActivity {
	@Inject
    TestViewModel vm;

	@InjectView(R.id.editText1)
	EditText mEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_test);
							
		mEditText.setText("Much wow");
	}

}
