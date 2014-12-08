package ch.hsr.nsg.themenrundgang.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import ch.hsr.nsg.themenrundgang.InjectionHelper;
import ch.hsr.nsg.themenrundgang.InjectionProvider;
import ch.hsr.nsg.themenrundgang.NsgApplication;

public abstract class BaseActivity extends Activity implements InjectionProvider {

	private final InjectionHelper helper;
	
	public BaseActivity(){
		helper = new InjectionHelper();
	}
	
	@Override
	public Activity getActivity() {
		return this;
	}
	
	@Override
	public NsgApplication getNsgApplication() {
		return (NsgApplication) getApplication();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		helper.onCreateImpl(this);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		helper.setContentViewImpl(this);
	}

	@Override
	protected void onDestroy() {
		helper.onDestoryImpl();
		super.onDestroy();
	}

	@Override
	public void inject(Object object) {
		helper.injectImpl(object);
	}
}
