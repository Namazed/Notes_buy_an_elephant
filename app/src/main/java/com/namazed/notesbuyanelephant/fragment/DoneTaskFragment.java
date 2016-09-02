package com.namazed.notesbuyanelephant.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namazed.notesbuyanelephant.R;


public class DoneTaskFragment extends Fragment {

    private RecyclerView mRecyclerViewDoneTasks;
    private RecyclerView.LayoutManager mLayoutManager;

    public DoneTaskFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_done_task, container, false);

        mRecyclerViewDoneTasks = (RecyclerView) rootView
                .findViewById(R.id.done_tasks_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewDoneTasks.setLayoutManager(mLayoutManager);

        return rootView;
    }

}
