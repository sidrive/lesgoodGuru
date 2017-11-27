package com.lesgood.guru.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lesgood.guru.R;

/**
 * Created by Agus on 4/20/17.
 */

public class DialogSchedule extends Dialog {
    OnDialogSchedluleClickListener mCallBack;

    private Context context;

    public interface OnDialogSchedluleClickListener {
        public void onCalenderClicked(Dialog dialog);
    }

    public DialogSchedule(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_schedule);
        ButterKnife.bind(this);
        setCancelable(true);

        init();

        try {
            mCallBack = (OnDialogSchedluleClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnDialogSchedluleClickListener");
        }

    }

    private void init() {

    }

    @OnClick(R.id.btn_hapus_jadwal)
    void positifClicked() {
        mCallBack.onCalenderClicked(this);
    }
}