package com.namazed.notesbuyanelephant.fragment;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.namazed.notesbuyanelephant.adapter.TaskAdapter;
import com.namazed.notesbuyanelephant.model.ModelTask;

public abstract class TaskFragment extends Fragment {
    protected RecyclerView mRecyclerViewTasks;
    protected RecyclerView.LayoutManager mLayoutManager;

    protected TaskAdapter mAdapter;

    public void addTask(ModelTask newTask) {
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
    }

    public abstract void moveTask(ModelTask task);
}
