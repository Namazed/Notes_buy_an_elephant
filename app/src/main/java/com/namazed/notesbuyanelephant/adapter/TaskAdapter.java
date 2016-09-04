package com.namazed.notesbuyanelephant.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.namazed.notesbuyanelephant.fragment.TaskFragment;
import com.namazed.notesbuyanelephant.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> mItems;
    TaskFragment mTaskFragment;

    public TaskAdapter(TaskFragment taskFragment) {
        mTaskFragment = taskFragment;
        mItems = new ArrayList<>();
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public void addItem(Item item) {
        mItems.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        mItems.add(location, item);
        notifyItemInserted(location);
    }

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            mItems.remove(location);
            notifyItemRemoved(location);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public TaskFragment getTaskFragment() {
        return mTaskFragment;
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTitle;
        protected TextView mDate;
        protected CircleImageView mPriority;

        public TaskViewHolder(View itemView, TextView title, TextView date,
                              CircleImageView priority) {
            super(itemView);
            mDate = date;
            mTitle = title;
            mPriority = priority;
        }
    }
}
