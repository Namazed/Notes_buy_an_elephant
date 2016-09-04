package com.namazed.notesbuyanelephant.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namazed.notesbuyanelephant.R;
import com.namazed.notesbuyanelephant.adapter.DoneTasksAdapter;
import com.namazed.notesbuyanelephant.model.ModelTask;


public class DoneTaskFragment extends TaskFragment {


    public DoneTaskFragment() {
    }

    OnTaskRestoreListener mOnTaskRestoreListener;

    public interface OnTaskRestoreListener {
        void onTaskRestore(ModelTask task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnTaskRestoreListener = (OnTaskRestoreListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTaskRestoreListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        mRecyclerViewTasks = (RecyclerView) rootView
                .findViewById(R.id.done_tasks_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTasks.setLayoutManager(mLayoutManager);

        mAdapter = new DoneTasksAdapter(this);
        mRecyclerViewTasks.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void moveTask(ModelTask task) {
        mOnTaskRestoreListener.onTaskRestore(task);
    }
}
