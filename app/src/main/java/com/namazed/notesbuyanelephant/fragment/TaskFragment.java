package com.namazed.notesbuyanelephant.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.namazed.notesbuyanelephant.MainActivity;
import com.namazed.notesbuyanelephant.adapter.TaskAdapter;
import com.namazed.notesbuyanelephant.model.ModelTask;

public abstract class TaskFragment extends Fragment {
    protected RecyclerView mRecyclerViewTasks;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected TaskAdapter mAdapter;

    public MainActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        addTaskFromDB();
    }

    public void addTask(ModelTask newTask, boolean saveToDB) {
        int position = -1;

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (mAdapter.getItem(i).isTask()) {
                ModelTask task = (ModelTask) mAdapter.getItem(i);
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;
                }
            }
        }

        if (position != -1) {
            mAdapter.addItem(position, newTask);
        } else {
            mAdapter.addItem(newTask);
        }

        if (saveToDB) {
            activity.dbHelper.saveTask(newTask);
        }
    }

    public abstract void addTaskFromDB();

    public abstract void moveTask(ModelTask task);
}
