package manager.app;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class Utils {

    public static TranslateAnimation getAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long durationMillis) {
        TranslateAnimation animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        animation.setDuration(durationMillis);
        animation.setFillAfter(true);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }
}
