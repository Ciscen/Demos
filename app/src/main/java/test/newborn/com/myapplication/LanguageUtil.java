package test.newborn.com.myapplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by xiaochongzi on 17-6-14
 */

public class LanguageUtil {
    private final static String LAN_FR = "fr";
    private final static String LAN_ES = "es";


    private static Resources getResourcesByLocale(Resources res, String localeName) {
        Configuration conf = new Configuration(res.getConfiguration());
        conf.locale = new Locale(localeName);
        return new Resources(res.getAssets(), res.getDisplayMetrics(), conf);
    }

    private static void resetLocale(Resources res) {
        Configuration configuration = res.getConfiguration();
        configuration.locale = Locale.getDefault();
        res.updateConfiguration(configuration, res.getDisplayMetrics());
    }

    public static String getTTSString(Context context, int id) {
        Resources resources = context.getResources();
        String string = getResourcesByLocale(resources, "").getString(id);
        resetLocale(resources);
        return string;
    }

    /**
     * check the app is installed
     */
    public static boolean isGoogleTTSInstalled(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.google.android.tts", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
