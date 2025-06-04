package manager.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;

/**
 * Base activity providing navigation helpers used across the app.
 */
public class BaseActivity extends Activity {

    /**
     * Start an activity clearing the task stack and applying the default
     * transition animation.
     *
     * @param destination the activity class to launch
     */
    protected void navigateTo(Class<? extends Activity> destination) {
        Intent intent = new Intent(this, destination)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent, ActivityOptions.makeCustomAnimation(
                getApplicationContext(),
                R.anim.animation_pre,
                R.anim.animation_post
        ).toBundle());
        System.gc();
    }
}
