package Utility;

import android.content.Context;

import static Utility.Const.SHARED_PREF_KEY_BASEURL;
import static Utility.Const.SHARED_PREF_KEY_EMAIL;
import static Utility.Const.SHARED_PREF_KEY_LOCATION;
import static Utility.Const.SHARED_PREF_NAME;

/**
 * Created by sc-147 on 10-Mar-18.
 */

public class MySharedPrefs {

    public static void saveLocation(Context c, String location) {
        c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_KEY_LOCATION, location)
                .apply();
    }

    public static String loadLocation(Context c) {
        return c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(SHARED_PREF_KEY_LOCATION, "");
    }

    public static void saveEmaiID(Context c, String email) {
        c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_KEY_EMAIL, email)
                .apply();
    }

    public static String loadEmail(Context c) {
        return c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(SHARED_PREF_KEY_EMAIL, "");
    }

    public static void saveUrl(Context c, String url) {
        c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREF_KEY_BASEURL, url)
                .apply();
    }

    public static String loadUrl(Context c) {
        return c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(SHARED_PREF_KEY_BASEURL, URL.baseURL);
    }

    public static void clearSharedpref(Context c) {
        c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
