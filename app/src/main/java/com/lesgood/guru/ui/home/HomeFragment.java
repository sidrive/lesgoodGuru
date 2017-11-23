package com.lesgood.guru.ui.home;


import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener;
import com.github.sundeepk.compactcalendarview.domain.Event;

import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BaseFragment;
import com.lesgood.guru.data.model.Days;
import com.lesgood.guru.data.model.TimeSchedule;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.util.TypefacedTextView;
import com.lesgood.guru.util.Utils;
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
  DaysAdapter daysAdapter;

  @Inject
  TimesAdapter timesAdapter;

  @Inject
  MainActivity activity;

  @Bind(R.id.cv_schedule)
  CompactCalendarView cvSchedule;

  @Bind(R.id.tvDateTitle)
  TypefacedTextView tvDateTitle;
  @Bind(R.id.rcvDay)
  RecyclerView rcvDay;
  @Bind(R.id.rcvTime)
  RecyclerView rcvTime;


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
  public void onStart() {
    super.onStart();
    presenter.subscribe();
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.subscribe();
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
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);
    getActivity().setTitle("Lesgood Pengajar");
    presenter.getDaySchedule();
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
      showDialogSetJadwal(date.getTime());
    }

    @Override
    public void onMonthScroll(Date date) {
      tvDateTitle.setText(Utils.dateToString(date));
    }
  };

  public void init() {
    Log.e("init", "HomeFragment" + user.isActive());
    sStatus.setChecked(user.isActive());
    //initSchedule();
    if (user.isActive()) {
      sStatus.setText("Status : Aktif");
    } else {
      sStatus.setText("Status : Tidak Aktif");
    }
  }

  public void showItemsDays() {
    rcvDay.setAdapter(daysAdapter);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    rcvDay.setLayoutManager(linearLayoutManager);
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
    Builder builder = new Builder(getContext());
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_schedule, null, false);
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

  public void addToScheduleToCalneder(List<Event> events) {
    cvSchedule.addEvents(events);
  }



  public void showAddedItem(Days item) {
   daysAdapter.onItemAdded(item);
    showItemsDays();
  }
  public void addTimeToAdapter(TimeSchedule item){
    timesAdapter.onItemAdded(item);
    showtimeDetailSchedule();
  }

  private void showtimeDetailSchedule() {
    rcvTime.setAdapter(timesAdapter);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
    rcvTime.setLayoutManager(gridLayoutManager );
  }

  public void showTimePicker(Days item) {
    Calendar cal = Calendar.getInstance();
    TimePickerDialog timePickerDialog =   new TimePickerDialog(
        getContext(),
        (view, hourOfDay, minute) -> {
          String time = hourOfDay+":"+minute;

          presenter.setTimeSchedule(item.getName(),time);
        },
        cal.get(Calendar.HOUR),
        cal.get(Calendar.MINUTE), true);
    timePickerDialog.show();

  }


  public void showDetailListSchedule() {
    presenter.showDetailScheduleByDay();
  }
}
