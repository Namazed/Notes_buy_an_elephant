package com.namazed.notesbuyanelephant;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";

    private static PreferenceHelper instance;

    private Context mContext;
    private SharedPreferences mPreferences;

    private PreferenceHelper() {

    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

}
