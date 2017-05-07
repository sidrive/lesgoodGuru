package com.lesgood.guru.data.remote;

import android.app.Application;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.data.model.EmailConfirmation;
import com.lesgood.guru.data.model.OTPdata;
import com.lesgood.guru.data.model.User;


/**
 * Created by Agus on 2/21/17.
 */

public class UserService {

    private Application application;
    private DatabaseReference databaseRef;

    public UserService(Application application) {
        this.application = application;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public void createUser(User user) {
        if(user.getPhoto_url() == null) {
            user.setPhoto_url("NOT");
        }
        databaseRef.child("users").child(user.getUid()).setValue(user);

    }


    public DatabaseReference getUser(String userUid) {
        return databaseRef.child("users").child(userUid);
    }

    public DatabaseReference getUsers(){
        return databaseRef.child("users");
    }


    public Task<Void> updateUser(User user) {
        return databaseRef.child("users").child(user.getUid()).setValue(user);
    }

    public void deleteUser(String key) {

    }

    public void updateUserToken(String uid, String token){
        databaseRef.child("user-fcm-token").child(uid).setValue(token);
    }

    public void sendEmailConfirmation(EmailConfirmation emailConfirmation){
        databaseRef.child("confirmationEmailRequest").push().setValue(emailConfirmation);
    }

    public DatabaseReference checkVerification(String uid){
        return databaseRef.child("borrower").child(uid);
    }

    public void updateOTP(OTPdata otp){
        databaseRef.child("OTP").child(otp.getKey()).setValue(otp);
    }

    public DatabaseReference verifyOTP(String key){
        return databaseRef.child("OTP").child(key);
    }

    public DatabaseReference getBorrowerCampaign(){
        return databaseRef.child("campaigns");
    }

    public void verifyUser(String uid, boolean status){
        databaseRef.child("users").child(uid).child("verified").setValue(status);
    }
}
