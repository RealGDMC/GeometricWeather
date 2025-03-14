package wangdaye.com.geometricweather.common.utils.helpers;

import android.util.Log;

public class LogHelper {

    private static final boolean DEBUG = true;

    private static final String TAG = "testing";

    public static void log(String msg) {
        log(TAG, msg);
    }

    public static void log(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }
}
