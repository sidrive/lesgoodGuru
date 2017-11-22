package com.lesgood.guru.ui.reviews;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.Reviews;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by Agus on 6/1/17.
 */

public class ReviewsPresenter implements BasePresenter {
    ReviewsActivity activity;
    UserService userService;
    User user;
    DatabaseReference databaseRef;
    ChildEventListener childEventListener;

    public ReviewsPresenter(ReviewsActivity activity, UserService userService, User user){
        this.activity = activity;
        this.userService = userService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {
        if (user != null){
            getReviews();
        }

    }

    @Override
    public void unsubscribe() {
        if (childEventListener != null) databaseRef.removeEventListener(childEventListener);
    }

    public void getReviews(){

        childEventListener = userService.getUserReviews(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Reviews reviews = dataSnapshot.getValue(Reviews.class);
                if (reviews != null){

                    activity.showAddedItem(reviews);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Reviews reviews = dataSnapshot.getValue(Reviews.class);
                if (reviews != null){
                    activity.showChangedItem(reviews);
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Reviews reviews = dataSnapshot.getValue(Reviews.class);
                if (reviews != null){
                    activity.showRemovedItem(reviews);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               /* AppUtils.showToas(activity.getApplicationContext(),databaseError.getMessage());*/
            }
        });
    }
}
