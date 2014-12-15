package ch.hsr.nsg.themenrundgang.utils;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by dominik on 15.12.14.
 */
public class ThemeHelper {

    public static void changeColorTheme(Themable themable, int colorDark, int colorVibrant) {
        themable.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        themable.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        themable.getWindow().setStatusBarColor(colorDark);
        themable.getWidget().setBackgroundColor(colorVibrant);
    }

    public static interface Themable {
        Window getWindow();
        View getWidget();
    }
}
