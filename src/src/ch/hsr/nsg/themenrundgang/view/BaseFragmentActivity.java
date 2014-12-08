package ch.hsr.nsg.themenrundgang.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import ch.hsr.nsg.themenrundgang.InjectionHelper;
import ch.hsr.nsg.themenrundgang.InjectionProvider;
import ch.hsr.nsg.themenrundgang.NsgApplication;

public abstract class BaseFragmentActivity extends FragmentActivity implements InjectionProvider {

	
	private InjectionHelper helper;
	
	public BaseFragmentActivity() {
		helper = new InjectionHelper();
	}
	
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

