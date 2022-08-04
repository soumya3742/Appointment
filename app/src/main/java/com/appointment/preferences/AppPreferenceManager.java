package com.appointment.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferenceManager {

    private static final String PREF_NAME = "UTopperPreference";
    private static final String PREF_USER_Id = "User_Id";
    public static final String PREF_USER_NAME = "User_Name";
    public static final String PREF_USER_EMAIL = "User_Email";
    public static final String PREF_USER_PHONE = "User_Phone";
    public static final String PREF_REPORTER = "reporter";
    private static final String PREF_PACKAGE_EXPIRE = "package_expire";
    private static final String PREF_PRIME_STATUS = "prime_status";

    private static SharedPreferences mSharedPreferences;
    public static SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static String getString(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");

    }

    public static void setUserId(Context context, int name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(PREF_USER_Id, name);
        editor.apply();
    }

    public static void setUserName(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_NAME, name);
        editor.apply();
    }

    public static void setUserEmail(Context context, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_EMAIL, email);
        editor.apply();
    }

    public static void setUserPhone(Context context, String phone) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_USER_PHONE, phone);
        editor.apply();
    }

    public static void setReporter(Context context, String phone) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_REPORTER, phone);
        editor.apply();
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_NAME, "");
    }

    public static String getReporter(Context context) {
        return getSharedPreferences(context).getString(PREF_REPORTER, "");
    }

    public static int getUserId(Context context) {
        return getSharedPreferences(context).getInt(PREF_USER_Id, 0);
    }

    public static void setPrimeExpiredDate(Context context, String PrimeExpireDate) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PREF_PACKAGE_EXPIRE, PrimeExpireDate);
        editor.apply();
    }

    public static void setPrimeActive(Context context, String primeStatus) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        String str = AppPreferenceManager.getUserId(context) + "prime" + primeStatus + AppPreferenceManager.getUserId(context);
        editor.putString(PREF_PRIME_STATUS, str);
        editor.apply();
    }



    public static void clearAll(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}
