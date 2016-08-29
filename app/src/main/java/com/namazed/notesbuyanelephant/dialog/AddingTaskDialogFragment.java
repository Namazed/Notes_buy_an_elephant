package com.namazed.notesbuyanelephant.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.namazed.notesbuyanelephant.R;
import com.namazed.notesbuyanelephant.Utils;

import java.util.Calendar;

public class AddingTaskDialogFragment extends DialogFragment {

    private View mContainer;
    private TextInputLayout mTilTitle;
    private EditText mEditTextTitle;
    private EditText mEditTextDate;
    private EditText mEditTextTime;
    private Button mPositiveButton;
    private AddingTaskListener mAddingTaskListener;

    public interface AddingTaskListener {
        void onTaskAdded();

        void onTaskAddingCancel();
    }

    //todo will go to support Fragment and onAttach(Context context)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mAddingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        mContainer = inflater.inflate(R.layout.dialog_task, null);

        initTextInputLayout();

        builder.setView(mContainer);

        afterClickForDate();
        afterClickForTime();

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAddingTaskListener.onTaskAdded();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAddingTaskListener.onTaskAddingCancel();
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                mPositiveButton = ((AlertDialog) dialogInterface)
                        .getButton(DialogInterface.BUTTON_POSITIVE);
                if (mEditTextTitle.length() == 0) {
                    //Protection against the creation of empty notes
                    mPositiveButton.setEnabled(false);
                    mTilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }
                setTextTitle();
            }
        });

        return alertDialog;
    }


    public void initTextInputLayout() {
        mTilTitle = (TextInputLayout)
                mContainer.findViewById(R.id.til_dialog_task_title);
        mEditTextTitle = mTilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout)
                mContainer.findViewById(R.id.til_dialog_task_date);
        mEditTextDate = tilDate.getEditText();

        TextInputLayout tilTime = (TextInputLayout)
                mContainer.findViewById(R.id.til_dialog_task_time);
        mEditTextTime = tilTime.getEditText();

        mTilTitle.setHint(getResources().getString(R.string.task_title_hint));
        tilDate.setHint(getResources().getString(R.string.task_date_hint));
        tilTime.setHint(getResources().getString(R.string.task_time_hint));
    }


    private void afterClickForDate() {
        mEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextDate.length() == 0) {
                    mEditTextDate.setText(" ");
                }

                DialogFragment datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar dateCalendar = Calendar.getInstance();
                        dateCalendar.set(i, i1, i2);
                        mEditTextDate.setText(Utils.getDate(dateCalendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mEditTextDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
            }
        });
    }


    private void afterClickForTime() {
        mEditTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextTime.length() == 0) {
                    mEditTextTime.setText(" ");
                }

                DialogFragment timePickerFragment = new TimePickerFragment() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Calendar timeCalendar = Calendar.getInstance();
                        timeCalendar.set(0, 0, 0, i, i1);
                        mEditTextTime.setText(Utils.getTime(timeCalendar.getTimeInMillis()));
                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mEditTextTime.setText(null);
                    }
                };
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });
    }


    private void setTextTitle() {
        mEditTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    mPositiveButton.setEnabled(false);
                    mTilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                } else {
                    mPositiveButton.setEnabled(true);
                    mTilTitle.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
