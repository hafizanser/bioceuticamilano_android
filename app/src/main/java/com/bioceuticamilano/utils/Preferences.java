package com.bioceuticamilano.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bioceuticamilano.ui.Constants;
import com.bioceuticamilano.model.Card;
import com.bioceuticamilano.model.UserModel;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;


public class Preferences {
    private static final String SHARED_PREFRENCES_KEY = "UserSharedPrefs";
    public static final String DEVICE_TOKEN = "deviceToken";
    public static final String WHATSAPP = "whatsapp";

    public static void saveSharedPrefValue(Context mContext, String key, String value) {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putString(key, scrambleText(value));
        edit.apply();
    }

    public static void saveBoolSharedPrefValue(Context mContext, String key, boolean value) {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static void saveIntegerSharedPrefValue(Context mContext, String key, int value) {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static void saveFloatSharedPrefValue(Context cxt, String key, float value) {
        SharedPreferences UserSharedPrefs = cxt.getSharedPreferences("UserSharedPrefs", Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putFloat(key, value);
        edit.apply();
    }

    /**********************************************************************************************
     *
     * @param cxt
     * @param key
     * @return
     */
    public static boolean getBoolSharedPrefValue(Context cxt, String key, boolean defaultValue) {
        SharedPreferences UserSharedPrefs = cxt.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        return UserSharedPrefs.getBoolean(key, defaultValue);
    }

    public static String getSharedPrefValue(Context mContext, String key) {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        String value = UserSharedPrefs.getString(key, null);
        return unScrambleText(value);
    }

    public static String getSharedPrefValue(Context mContext, String key, String defaultValue) {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        String value = UserSharedPrefs.getString(key, defaultValue);
        return unScrambleText(value);
    }

    public static SharedPreferences getSharedPref(Context cxt) {
        return cxt.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
    }

    public static int getIntSharedPrefValue(Context cxt, String shared_pref_key, int defaultValue) {
        SharedPreferences UserSharedPrefs = cxt.getSharedPreferences("UserSharedPrefs", Activity.MODE_PRIVATE);
        return UserSharedPrefs.getInt(shared_pref_key, defaultValue);
    }

    public static float getFloatSharedPrefValue(Context cxt, String shared_pref_key, float defaultValue) {
        SharedPreferences UserSharedPrefs = cxt.getSharedPreferences("UserSharedPrefs", Activity.MODE_PRIVATE);
        return UserSharedPrefs.getFloat(shared_pref_key, defaultValue);
    }

    /**
     * Convenience method to save a string value
     * @param context The context
     * @param key The key to save the value under
     * @param value The string value to save
     */
    public static void saveString(Context context, String key, String value) {
        saveSharedPrefValue(context, key, value);
    }

    /**
     * Convenience method to get a string value
     * @param context The context
     * @param key The key to retrieve the value from
     * @return The string value, or null if not found
     */
    public static String getString(Context context, String key) {
        return getSharedPrefValue(context, key);
    }

    /**
     * Convenience method to get a string value with default
     * @param context The context
     * @param key The key to retrieve the value from
     * @param defaultValue The default value to return if key not found
     * @return The string value, or defaultValue if not found
     */
    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPrefValue(context, key, defaultValue);
    }


    /*******************************************************************************************
     *
     * @param mContext
     * @return
     */


    public static UserModel getUserDetails(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        String val = sharedPreferences.getString(Constants.USERDEFAULT_USER_DATA, null);
        return new Gson().fromJson(val, UserModel.class);

    }

    public static void saveLoginDefaults(Context mContext, UserModel myObject) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        editor.putString(Constants.USERDEFAULT_USER_DATA, json);
        editor.putBoolean(Constants.USERDEFAULT_ISLOGGEDIN, true);
        editor.apply();
    }

    public static void saveObject(Context mContext, Object myObject, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        editor.putString(key, json);
        editor.apply();
    }

    public static Object getObject(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        String val = sharedPreferences.getString(key, null);
        return new Gson().fromJson(val, Object.class);

    }


    public static void saveObjectList(Context mContext, List<Object> myObject, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        editor.putString(key, json);
        editor.apply();
    }




    public static void saveStringList(Context mContext, List<String> myObject, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        editor.putString(key, json);
        editor.apply();
    }


    public static void removeKey(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
    public static void isUserLoggedIn(Context mContext,boolean loggedIn) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.USERDEFAULT_ISLOGGEDIN,loggedIn).apply();
    }
    public static boolean isUserLoggedIn(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.USERDEFAULT_ISLOGGEDIN, false);
    }

    public static void logoutDefaults(Context mContext) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        Preferences.saveSharedPrefValue(mContext, Constants.USERDEFAULT_GCM_STRING, "");
        Preferences.saveBoolSharedPrefValue(mContext, Constants.USERDEFAULT_ISLOGGEDIN, false);

        String gcmToken = Preferences.getSharedPrefValue(mContext, Constants.USERDEFAULT_GCM_STRING);
        editor.clear();
        editor.putString(Constants.USERDEFAULT_GCM_STRING, scrambleText(gcmToken));
        editor.apply();
    }

    public static void deleteKey(Context mContext, String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

//    public static TopDestinationModel getCityObject(Context mContext, String key) {
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
//        String val = sharedPreferences.getString(key, null);
//        return new Gson().fromJson(val, TopDestinationModel.class);
//    }

    private static String scrambleText(String text) {
        try {
            Random r = new Random();
            String prefix = String.valueOf(r.nextInt(90000) + 10000);

            String suffix = String.valueOf(r.nextInt(90000) + 10000);

            text = prefix + text + suffix;

            byte[] bytes = text.getBytes("UTF-8");
            byte[] newBytes = new byte[bytes.length];

            for (int i = 0; i < bytes.length; i++) {
                newBytes[i] = (byte) (bytes[i] - 1);
            }

            return new String(newBytes, "UTF-8");
        } catch (Exception e) {
            return text;
        }
    }

    private static String unScrambleText(String text) {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            byte[] newBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                newBytes[i] = (byte) (bytes[i] + 1);
            }
            String textVal = new String(newBytes, "UTF-8");

            return textVal.substring(5, textVal.length() - 5);
        } catch (Exception e) {
            return text;
        }
    }

    public static void saveDefaultCard(Context context, Card card) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(card);
        editor.putString("default_card", json);
        editor.apply();
    }

    public static Card getDefaultCard(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        String json = sharedPreferences.getString("default_card", null);
        if (json == null) return null;
        return new Gson().fromJson(json, Card.class);
    }

    public static void deleteDefaultCard(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove("default_card"); // remove the key
        editor.apply(); // save changes asynchronously
    }

}
