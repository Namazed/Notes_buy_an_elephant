package com.namazed.notesbuyanelephant.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namazed.notesbuyanelephant.R;
import com.namazed.notesbuyanelephant.Utils;
import com.namazed.notesbuyanelephant.model.Item;
import com.namazed.notesbuyanelephant.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

public class CurrentTasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> mItems = new ArrayList<>();

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TASK:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_task, parent, false);
                TextView title = (TextView) v.findViewById(R.id.text_view_task_title);
                TextView date = (TextView) v.findViewById(R.id.text_view_task_date);
                return new TaskViewHolder(v, title, date);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mItems.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            ModelTask task = (ModelTask) item;
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            taskViewHolder.mTitle.setText(task.getTitle());
            if (task.getDate() != 0) {
                taskViewHolder.mDate.setText(Utils.getFullDate(task.getDate()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()) {
            return TYPE_TASK;
        } else {
            return TYPE_SEPARATOR;
        }
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mDate;

        public TaskViewHolder(View itemView, TextView title, TextView date) {
            super(itemView);
            mDate = date;
            mTitle = title;
        }
    }
}
