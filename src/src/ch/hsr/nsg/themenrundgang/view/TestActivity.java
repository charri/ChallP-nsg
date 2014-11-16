package ch.hsr.nsg.themenrundgang.view;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.vm.TestViewModel;

public class TestActivity extends BaseActivity {
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
