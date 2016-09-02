package com.namazed.notesbuyanelephant.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.namazed.notesbuyanelephant.fragment.CurrentTaskFragment;
import com.namazed.notesbuyanelephant.fragment.DoneTaskFragment;

public class TabAdapter extends FragmentStatePagerAdapter {
    /*
    I can use this adapter from support library v4(FragmentStatePagerAdapter),
    may then be support old version API with fragments.
     */
    private int mNumberOfTabs;

    public static final int CURRENT_TASK_FRAGMENT_POSITION = 0;
    public static final int DONE_TASK_FRAGMENT_POSITION = 1;

    private CurrentTaskFragment mCurrentTaskFragment;
    private DoneTaskFragment mDoneTaskFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        mNumberOfTabs = numberOfTabs;
        mCurrentTaskFragment = new CurrentTaskFragment();
        mDoneTaskFragment = new DoneTaskFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mCurrentTaskFragment;
            case 1:
                return mDoneTaskFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
