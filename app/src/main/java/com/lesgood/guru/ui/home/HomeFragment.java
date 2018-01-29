package com.lesgood.guru.ui.home;


import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;


import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.base.BaseFragment;
import com.lesgood.guru.data.model.Days;
import com.lesgood.guru.data.model.TimeSchedule;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.util.CustomTimePiker;
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

  @Bind(R.id.s_active)
  SwitchCompat sStatus;
  @Inject
  HomePresenter presenter;
  @Inject
  User user;

  @Inject
  DaysAdapter daysAdapter;



  @Inject
  MainActivity activity;

  @Bind(R.id.tvStatusUser)
  TypefacedTextView tvStsUser;

  @Bind(R.id.tvDateTitle)
  TypefacedTextView tvDateTitle;
  @Bind(R.id.rcvDay)
  RecyclerView rcvDay;
  @Bind(R.id.rcvTime)
  RecyclerView rcvTime;
  private FirebaseTimeScheduleAdapter firebaseTimeScheduleAdapter;
  private Query queryTimeSchedule;

  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  public HomeFragment() {}

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
    Bundle ex = getArguments();
    if (ex!=null){
      String param = ex.getString("param");
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    ButterKnife.bind(this, view);
    getActivity().setTitle("Lesgood Pengajar");
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    queryTimeSchedule = FirebaseDatabase.getInstance().getReference().child("user-schedules").orderByChild("id").equalTo(user.getUid());
    firebaseTimeScheduleAdapter =  new FirebaseTimeScheduleAdapter(
        TimeSchedule.class,
        R.layout.list_item_time,
        TimesViewHolder.class,
        queryTimeSchedule,this);
    init();
  }

  private void initSchedule() {
    Calendar c = Calendar.getInstance();
    Date date = c.getTime();
    tvDateTitle.setText(Utils.dateToString(date));
  }



  @SuppressLint("ResourceAsColor")
  public void init() {
    if (user.getActive()!=null){
      if (user.getActive()) {
        sStatus.setText("Status : Aktif");
      } else {
        sStatus.setText("Status : Tidak Aktif");
      }
    }
    sStatus.setChecked(user.getActive());

    if (user.getVerified()!=null){
      if (user.getVerified()) {
        tvStsUser.setText("SELAMAT MENGAJAR");
      } else {
        tvStsUser.setText("Anda Belum Terverifikasi");
        tvStsUser.setBackgroundColor(R.color.colorGrey800);
      }

    }
    presenter.getDaySchedule();
    presenter.showDetailScheduleByDay();
    initSchedule();
  }

  public void showItemsDays() {
    rcvDay.setAdapter(daysAdapter);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    rcvDay.setLayoutManager(linearLayoutManager);
  }

  @OnCheckedChanged(R.id.s_active )
  void onStatusChanged(boolean status) {
    Log.e("onStatusChanged", "HomeFragment" + status);

    presenter.updaeStatus(user.getUid(), status);
    if (status) {
      sStatus.setText("Status : Aktif");
    } else {
      sStatus.setText("Status : Tidak Aktif");
    }
    user.setActive(status);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }
  public void showDeleteTimeSchedule(String id_schedule) {
    Builder builder = new Builder(getContext());
    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_schedule, null, false);
    builder.setView(view);
    Button btn = (Button) view.findViewById(R.id.btn_hapus_jadwal);
    builder.setCancelable(true);
    Dialog dialog = builder.create();
    btn.setOnClickListener(v -> {
      dialog.dismiss();
      presenter.deleteSchedule(id_schedule);
    });
    dialog.show();
  }
  public void showAddedItem(Days item) {
    daysAdapter.onItemAdded(item);
    showItemsDays();
  }


  public void showtimeDetailSchedule() {
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
    rcvTime.setLayoutManager(gridLayoutManager );
    rcvTime.setAdapter(firebaseTimeScheduleAdapter);
  }


  public void showStarTimePicker(Days item) {
    CustomTimePiker timePiker = new CustomTimePiker(presenter,item.getName());
    timePiker.show(getFragmentManager(),"timepicker");
  }


  public void deleteTimeSchedule(String schedule_id) {
    showDeleteTimeSchedule(schedule_id);
  }


  public void updateStatus(boolean isActive) {
    if (isActive) {
      sStatus.setText("Status : Aktif");
    } else {
      sStatus.setText("Status : Tidak Aktif");
    }
  }


  public void showError(String message) {
    Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
  }



  public void OnTimeScheduleListener(ChildEventListener childEventListener) {
    queryTimeSchedule.addChildEventListener(childEventListener);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
    rcvTime.setLayoutManager(gridLayoutManager );
    rcvTime.setAdapter(firebaseTimeScheduleAdapter);
  }

  public void notifDataChange() {
    firebaseTimeScheduleAdapter.notifyDataSetChanged();
  }
}
