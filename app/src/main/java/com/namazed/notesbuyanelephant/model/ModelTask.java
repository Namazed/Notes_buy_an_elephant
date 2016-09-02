package com.namazed.notesbuyanelephant.model;


public class ModelTask implements Item {

    private String mTitle;
    private long mDate;

    public ModelTask() {

    }

    public ModelTask(String title, long date) {
        mDate = date;
        mTitle = title;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
