package com.lesgood.guru.ui.home;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import com.alamkanak.weekview.WeekViewEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BaseFragment;

import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.dialog.DialogSchedule;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.util.TypefacedTextView;
import com.lesgood.guru.util.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Agus on 4/27/17.
 */

public class HomeFragment extends BaseFragment {


  @BindColor(R.color.colorAccentDark)
  int colorGreen;

  /*@Bind(R.id.weekView)
  WeekView weekView;*/

  @Bind(R.id.s_active)
  SwitchCompat sStatus;

  @Inject
  HomePresenter presenter;

  @Inject
  User user;

  @Inject
  MainActivity activity;
  @Bind(R.id.cv_schedule)
  CompactCalendarView cvSchedule;
  @Bind(R.id.tvDateTitle)
  TypefacedTextView tvDateTitle;


  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  public HomeFragment() {
    // Required empty public constructor
  }

  @Override
  protected void setupFragmentComponent() {
    BaseApplication.get(getActivity())
        .getMainComponent()
        .plus(new HomeFragmentModule(this))
        .inject(this);
  }

  @Override
  public void onResume() {
    super.onResume();

    presenter.subscribe();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.unsubscribe();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    //returning our layout file
    //change R.layout.yourlayoutfilename for each of your fragments
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);

    getActivity().setTitle("Lesgood Pengajar");

    init();
    return view;
  }

  private void initSchedule() {
    Calendar c = Calendar.getInstance();
    Date date = c.getTime();
    tvDateTitle.setText(Utils.dateToString(date));
    cvSchedule.setListener(compactCalendarViewListener);

  }

  public CompactCalendarViewListener compactCalendarViewListener = new CompactCalendarViewListener() {
    @Override
    public void onDayClick(Date date) {
      Log.e("onDayClick", "HomeFragment" + date.getTime());
      showDialogSetJadwal(date.getTime());
    }

    @Override
    public void onMonthScroll(Date date) {
      Log.e("onMonthScroll", "HomeFragment" + date);
      tvDateTitle.setText(Utils.dateToString(date));
    }
  };

  public void init() {
    sStatus.setChecked(user.isActive());
    initSchedule();
    if (user.isActive()) {
      sStatus.setText("Status : Aktif");
    } else {
      sStatus.setText("Status : Tidak Aktif");
    }
  }



  @OnCheckedChanged(R.id.s_active)
  void onStatusChanged(boolean status) {
    presenter.updaeStatus(user.getUid(), status);
    if (status) {
      sStatus.setText("Status : Aktif");
    } else {
      sStatus.setText("Status : Tidak Aktif");
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  public void showDialogSetJadwal(long date) {

    AlertDialog.Builder builder = new Builder(getContext());
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_schedule,null,false);
    builder.setView(view);
    Button btn = (Button) view.findViewById(R.id.btn_jadwal);
    Button btnHapus = (Button) view.findViewById(R.id.btn_hapus_jadwal);

    builder.setCancelable(true);
    Dialog dialog = builder.create();
    btn.setOnClickListener(v -> {
      dialog.dismiss();
      presenter.createSchedule(date);
    });
    btnHapus.setOnClickListener(v -> {
      dialog.dismiss();
      presenter.deleteSchedule(date);
    });
    dialog.show();
  }
  public void addToScheduleToCalneder(List<Event> events){
    cvSchedule.addEvents(events);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.getUserSchedule();
  }
}
