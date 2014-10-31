package ch.hsr.nsg.themenrundgang.view;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.os.Bundle;
import android.widget.EditText;
import ch.hsr.nsg.themenrundgang.R;
import ch.hsr.nsg.themenrundgang.model.ItemRepository;

public class TestActivity extends BaseActivity {
	@Inject
	ItemRepository repository;

	@InjectView(R.id.editText1)
	EditText mEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_test);
		ButterKnife.inject(this);
		
		String text = repository.Foo();
		// repository has been injected
		
		mEditText.setText(text);
	}

}
