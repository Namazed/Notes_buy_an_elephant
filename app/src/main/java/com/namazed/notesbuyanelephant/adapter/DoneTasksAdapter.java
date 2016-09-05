package com.namazed.notesbuyanelephant.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.namazed.notesbuyanelephant.R;
import com.namazed.notesbuyanelephant.Utils;
import com.namazed.notesbuyanelephant.fragment.DoneTaskFragment;
import com.namazed.notesbuyanelephant.model.Item;
import com.namazed.notesbuyanelephant.model.ModelTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoneTasksAdapter extends TaskAdapter {

    private ModelTask mTask;
    private TaskViewHolder mTaskViewHolder;
    private View mItemView;
    private Resources mResources;

    public DoneTasksAdapter(DoneTaskFragment taskFragment) {
        super(taskFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_task, parent, false);
        TextView title = (TextView) v.findViewById(R.id.text_view_task_title);
        TextView date = (TextView) v.findViewById(R.id.text_view_task_date);
        CircleImageView priority = (CircleImageView) v.findViewById(R.id.cv_task_priority);

        return new TaskViewHolder(v, title, date, priority);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = mItems.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            mTask = (ModelTask) item;
            mTaskViewHolder = (TaskViewHolder) holder;

            mItemView = mTaskViewHolder.itemView;
            mResources = mItemView.getResources();

            mTaskViewHolder.mTitle.setText(mTask.getTitle());
            if (mTask.getDate() != 0) {
                mTaskViewHolder.mDate.setText(Utils.getFullDate(mTask.getDate()));
            } else {
                mTaskViewHolder.mDate.setText(null);
            }

            mItemView.setVisibility(View.VISIBLE);

            setColorForBackgroundAndText(
                    R.color.gray_200,
                    R.color.override_primary_text_disabled_material_light,
                    R.color.override_secondary_text_disabled_material_light
            );

            mTaskViewHolder.mPriority.setImageResource(R.drawable.ic_tag_faces_48dp);
            mTaskViewHolder.mPriority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTask.setStatus(ModelTask.STATUS_CURRENT);

                    getTaskFragment().activity.dbHelper.getUpdateManager()
                            .status(mTask.getTimeStamp(), ModelTask.STATUS_CURRENT);

                    setColorForBackgroundAndText(
                            R.color.gray_50,
                            R.color.override_primary_text_default_material_light,
                            R.color.override_secondary_text_default_material_light
                    );

                    mTaskViewHolder.mPriority
                            .setImageResource(R.drawable.ic_checkbox_marked_circle_outline);

                    animationFlipTask();
                }
            });
        }
    }

    @SuppressWarnings("deprecation")
    private void setColorForBackgroundAndText(int colorBackgroundItem, int colorTitleItem,
                                              int colorDateItem) {
        mItemView.setBackgroundColor(mResources.getColor(colorBackgroundItem));
        mTaskViewHolder.mTitle.setTextColor(mResources
                .getColor(colorTitleItem));
        mTaskViewHolder.mDate.setTextColor(mResources
                .getColor(colorDateItem));
        mTaskViewHolder.mPriority.setColorFilter(mResources
                .getColor(mTask.getPriorityColor()));
    }

    private void animationFlipTask() {
        //Animation for list item
        ObjectAnimator flipIn = ObjectAnimator
                .ofFloat(mTaskViewHolder.mPriority, "rotationY", 180f, 0f);
        flipIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (mTask.getStatus() != ModelTask.STATUS_DONE) {
                    ObjectAnimator translationX = ObjectAnimator.ofFloat(mItemView,
                            "translationX", 0f, -mItemView.getWidth());
                    ObjectAnimator translationXBack = ObjectAnimator.ofFloat(mItemView,
                            "translationX", -mItemView.getWidth(), 0f);

                    translationX.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            mItemView.setVisibility(View.GONE);
                            getTaskFragment().moveTask(mTask);
                            removeItem(mTaskViewHolder.getLayoutPosition());
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });

                    AnimatorSet translationSet = new AnimatorSet();
                    translationSet.play(translationX).before(translationXBack);
                    translationSet.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        flipIn.start();
    }
}
