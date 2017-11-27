package com.lesgood.guru.util;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lesgood.guru.R;
import com.lesgood.guru.ui.home.HomePresenter;
import java.util.Calendar;

/**
 * Created by sim-x on 11/27/17.
 */

@SuppressLint("ValidFragment")
public class CustomTimePiker extends DialogFragment {


  @Bind(R.id.tpMulai)
  TimePicker tpMulai;
  @Bind(R.id.tpSelesai)
  TimePicker tpSelesai;
  long startTime,endTime;

  HomePresenter presenter;
  String day;

  public CustomTimePiker(HomePresenter presenter, String day) {
    this.presenter = presenter;
    this.day = day;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_time, container, false);
    ButterKnife.bind(this, view);
    Calendar calendar = Calendar.getInstance();
    tpMulai.setIs24HourView(true);
    tpSelesai.setIs24HourView(true);
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
      tpMulai.setHour(calendar.get(Calendar.HOUR_OF_DAY));
      tpMulai.setMinute(calendar.get(Calendar.MINUTE));
      tpSelesai.setHour(calendar.get(Calendar.HOUR_OF_DAY));
      tpSelesai.setMinute(calendar.get(Calendar.MINUTE));
    }

    tpMulai.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,hourOfDay,minute);
      startTime = cal.getTimeInMillis();
    });
    tpSelesai.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
      Calendar cal = Calendar.getInstance();
      cal.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,hourOfDay,minute);
      endTime = cal.getTimeInMillis();
    });
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }


  @OnClick(R.id.btnSimpan)
  public void onViewClicked() {
   presenter.setTimeSchedule(this.day,startTime,endTime);
    this.dismiss();
  }
}
