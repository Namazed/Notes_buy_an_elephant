package com.namazed.notesbuyanelephant.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namazed.notesbuyanelephant.R;


public class DoneTaskFragment extends Fragment {


    public DoneTaskFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_done_task, container, false);
    }

}
