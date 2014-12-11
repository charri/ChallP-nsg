package ch.hsr.nsg.themenrundgang.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by tommy on 11.12.14.
 */
public class AnimationUtils {

    private final Context mContext;

    public AnimationUtils(Context context) {
        mContext = context;
    }

    public int dipsToPixels(int dips) {

        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int)(dips * scale + 0.5f);
    }

    public TranslateAnimation makeAnimation(final View target, final int from, final int to, final int marginTop)
    {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, dipsToPixels(from), dipsToPixels(to));
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("Animate", "Start " + dipsToPixels(from) + "," + dipsToPixels(to));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setTopMargin(target, marginTop);
                Animation nonFlicker = new TranslateAnimation(0, 0,0,0);
                nonFlicker.setDuration(0);
                target.setAnimation(nonFlicker);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void setTopMargin(View view, int topMargin)
    {

        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams)view.getLayoutParams();

        Log.d("Animate", "End - topMargin " + layoutParams.topMargin + "," + dipsToPixels(topMargin));
        layoutParams.topMargin = dipsToPixels(topMargin);
        view.requestLayout();
    }


    public static Point getCurrentTranslate(final View target) {
        float [] pos = new float [2];
        target.getMatrix().mapPoints(pos);
        return new Point((int)pos[0], (int)pos[1]);
    }
}
