package ch.hsr.nsg.themenrundgang;

import java.util.List;

import android.app.Activity;
import android.content.Context;

public interface InjectionProvider {
	public NsgApplication getNsgApplication();
	public Activity getActivity();
	public void inject(Object object);
}
