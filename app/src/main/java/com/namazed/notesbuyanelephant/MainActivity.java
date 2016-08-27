package com.namazed.notesbuyanelephant;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    private FragmentManager mFragmentManager;
    private PreferenceHelper mPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        PreferenceHelper.getInstance().init(getApplicationContext());
        mPreferenceHelper = PreferenceHelper.getInstance();

        mFragmentManager = getSupportFragmentManager();
        runSplash();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(mPreferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_splash:
                item.setChecked(!item.isChecked());
                mPreferenceHelper.putBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE, item.isChecked());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void runSplash() {
        if (!mPreferenceHelper.getBoolean(PreferenceHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            mFragmentManager.beginTransaction()
                    .replace(R.id.container, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
}
