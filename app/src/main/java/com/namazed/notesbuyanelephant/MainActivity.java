package com.namazed.notesbuyanelephant;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.namazed.notesbuyanelephant.adapter.TabAdapter;
import com.namazed.notesbuyanelephant.database.DBHelper;
import com.namazed.notesbuyanelephant.dialog.AddingTaskDialogFragment;
import com.namazed.notesbuyanelephant.fragment.CurrentTaskFragment;
import com.namazed.notesbuyanelephant.fragment.DoneTaskFragment;
import com.namazed.notesbuyanelephant.fragment.SplashFragment;
import com.namazed.notesbuyanelephant.fragment.TaskFragment;
import com.namazed.notesbuyanelephant.model.ModelTask;

public class MainActivity extends AppCompatActivity
        implements AddingTaskDialogFragment.AddingTaskListener,
        DoneTaskFragment.OnTaskRestoreListener, CurrentTaskFragment.OnTaskDoneListener {

    private static final int LAYOUT = R.layout.activity_main;

    private FragmentManager mFragmentManager;
    private PreferenceHelper mPreferenceHelper;
    private TabAdapter mTabAdapter;

    TaskFragment mCurrentTaskFragment;
    TaskFragment mDoneTaskFragment;

    public DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        PreferenceHelper.getInstance().init(getApplicationContext());
        mPreferenceHelper = PreferenceHelper.getInstance();

        dbHelper = new DBHelper(getApplicationContext());

        mFragmentManager = getFragmentManager();
        runSplash();
        setUI();
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
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    private void setUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getColor(getApplicationContext(), R.color.white));
            setSupportActionBar(toolbar);
        }

        initTabs();
        initFab();
    }

    private void initTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        mTabAdapter = new TabAdapter(mFragmentManager, 2);

        viewPager.setAdapter(mTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCurrentTaskFragment = (CurrentTaskFragment) mTabAdapter
                .getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);
        mDoneTaskFragment = (DoneTaskFragment) mTabAdapter
                .getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(mFragmentManager, "AddingTaskDialogFragment");
            }
        });
    }

    public static int getColor(Context context, int id) {
        /*
            For API 23, because getColor() is deprecated
         */
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            //noinspection deprecation
            return context.getResources().getColor(id);
        }
    }

    @Override
    public void onTaskAdded(ModelTask newTask) {
        mCurrentTaskFragment.addTask(newTask, true);
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(MainActivity.this, "No lopuh", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskDone(ModelTask task) {
        mDoneTaskFragment.addTask(task, false);
    }

    @Override
    public void onTaskRestore(ModelTask task) {
        mCurrentTaskFragment.addTask(task, false);
    }
}
