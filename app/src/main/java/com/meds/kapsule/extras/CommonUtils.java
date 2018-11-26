package com.meds.kapsule.extras;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class CommonUtils {
    public static void setFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
