package com.lesgood.guru.ui.home;

import android.graphics.Color;
import android.util.Log;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Days;
import com.lesgood.guru.data.model.TimeSchedule;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.util.AppUtils;
import com.lesgood.guru.util.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Agus on 4/27/17.
 */

public class HomePresenter implements BasePresenter {
    HomeFragment fragment;
    UserService userService;
    DatabaseReference databaseRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public HomePresenter(HomeFragment fragment, UserService userService){
        this.fragment = fragment;
        this.userService = userService;
        this.mAuth = FirebaseAuth.getInstance();
        this.mUser = mAuth.getCurrentUser();
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {
        getUserSchedule();
        fragment.showtimeDetailSchedule();
    }

    @Override
    public void unsubscribe() {
    }
    public void getDaySchedule(){
        userService.getDaySchedule().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Days days = dataSnapshot.getValue(Days.class);
                if (days.getName()!=null){
                    fragment.showAddedItem(days);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Days days = dataSnapshot.getValue(Days.class);
                if (days.getName()!=null){
                    fragment.showAddedItem(days);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Days days = dataSnapshot.getValue(Days.class);
                if (days.getName()!=null){
                    fragment.showAddedItem(days);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Days days = dataSnapshot.getValue(Days.class);
                if (days.getName()!=null){
                    fragment.showAddedItem(days);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onCancelled", "HomePresenter" + databaseError.getMessage());
            }
        });
    }
    public void createSchedule(long date){
        userService.createUserSchedule(mUser.getUid()).child(String.valueOf(date)).setValue(true)
            .addOnFailureListener(e -> {

                AppUtils.showToast(fragment.getContext(),e.getMessage());
            }).addOnSuccessListener(aVoid -> {
            getUserSchedule();
            AppUtils.showToast(fragment.getContext(), "SUKSES MENAMBAH JADWAL");
        });
    }
    public void getUserSchedule(){
        userService.getUserSchedule(mUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String date = dataSnapshot.getKey();
                List<Event> eventList = new ArrayList<>();
                eventList.add(new Event(Color.argb(252, 200, 64, 1),Long.parseLong(date),"Aviable"+ new Date(Long.parseLong(date))));
                fragment.addToScheduleToCalneder(eventList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String date = dataSnapshot.getKey();
                List<Event> eventList = new ArrayList<>();
                eventList.add(new Event(Color.argb(252, 200, 64, 1),Long.parseLong(date),"Aviable"+new Date(Long.parseLong(date))));
                fragment.addToScheduleToCalneder(eventList);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("onChildAdded", "HomePresenter" + databaseError.getMessage());
            }
        });
    }
    public void updaeStatus(String uid, boolean status){
        String stat = String.valueOf(status);
        userService.updateStatus(uid, stat);
    }
    public void deleteSchedule(String id){
        userService.removeUserTimeSchedule(id).addOnCompleteListener(task -> {

        });
    }
    public void setTimeSchedule(String day,long statTime, long endTime){
        TimeSchedule schedule = new TimeSchedule();
        String keypush = databaseRef.push().getKey();
        String dayFormated = Utils.dayFormated(day);
        schedule.setDay(day);
        schedule.setDayFormated(dayFormated);
        schedule.setSchedule_id(keypush);
        schedule.setId(mUser.getUid().toString());
        schedule.setDay_uid(day+"_"+mUser.getUid().toString());
        schedule.setStartTime(statTime);
        schedule.setEndTime(endTime);
        userService.setTimeSchedule().child(keypush).setValue(schedule)
            .addOnSuccessListener(aVoid -> {
                fragment.showtimeDetailSchedule();
            })

            .addOnFailureListener(e -> {
                Log.e("setTimeSchedule", "GAGAL");
            });
    }
    public void showDetailScheduleByDay(){
        userService.getUserTimeScheduleById(mUser.getUid()).addChildEventListener(
            new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    TimeSchedule timeSchedule = dataSnapshot.getValue(TimeSchedule.class);
                    if (dataSnapshot!=null){
                        fragment.addTimeToAdapter(timeSchedule);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    TimeSchedule timeSchedule = dataSnapshot.getValue(TimeSchedule.class);
                    if (dataSnapshot!=null){
                        fragment.timesAdapter.onItemChanged(timeSchedule);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    TimeSchedule timeSchedule = dataSnapshot.getValue(TimeSchedule.class);
                    if (dataSnapshot!=null){
                        fragment.showtimeDetailSchedule();
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot!=null){
                        fragment.timesAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("onCancelled", "HomePresenter" + databaseError.getMessage());
                }
            });
    }


}
