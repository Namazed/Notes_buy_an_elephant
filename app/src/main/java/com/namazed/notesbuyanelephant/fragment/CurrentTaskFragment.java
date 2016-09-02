package com.namazed.notesbuyanelephant.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namazed.notesbuyanelephant.R;
import com.namazed.notesbuyanelephant.adapter.CurrentTasksAdapter;
import com.namazed.notesbuyanelephant.model.ModelTask;

public class CurrentTaskFragment extends Fragment {

    private RecyclerView mRecyclerViewCurrentTasks;
    private RecyclerView.LayoutManager mLayoutManager;

    private CurrentTasksAdapter mAdapter;

    public CurrentTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_task, container, false);

        mRecyclerViewCurrentTasks = (RecyclerView) rootView
                .findViewById(R.id.current_tasks_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewCurrentTasks.setLayoutManager(mLayoutManager);
        mAdapter = new CurrentTasksAdapter();
        mRecyclerViewCurrentTasks.setAdapter(mAdapter);

        return rootView;
    }

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
}
