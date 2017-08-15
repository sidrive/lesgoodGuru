package com.lesgood.guru.data.remote;

import android.app.Application;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.guru.data.model.EmailConfirmation;
import com.lesgood.guru.data.model.OTPdata;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.Pengalaman;
import com.lesgood.guru.data.model.Prestasi;
import com.lesgood.guru.data.model.Skill;
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

    ///user

    public void createUser(User user) {
        if(user.getPhoto_url() == null) {
            user.setPhoto_url("NOT");
        }
        databaseRef.child("users").child(user.getUid()).setValue(user);

    }


    public DatabaseReference getUser(String userUid) {
        return databaseRef.child("users").child(userUid);
    }


    public Task<Void> updateUser(User user) {
        return databaseRef.child("users").child(user.getUid()).setValue(user);
    }

    public void deleteUser(String key) {

    }

    //users

    public void updateUserToken(String uid, String token){
        databaseRef.child("users").child(uid).child("guruTokens").child(token).setValue(true);
    }

    public void sendEmailConfirmation(EmailConfirmation emailConfirmation){
        databaseRef.child("confirmationEmailRequest").push().setValue(emailConfirmation);
    }

    public void verifyUser(String uid, boolean status){
        databaseRef.child("users").child(uid).child("verified").setValue(status);
    }

    // userabout

    public DatabaseReference getUserAbout(String uid){
        return databaseRef.child("users-about").child(uid);
    }


    public void updateAbout(String uid, String content){
        databaseRef.child("users-about").child(uid).setValue(content);
        databaseRef.child("users").child(uid).child("about").setValue(content);
    }

    //userabout


    //userskills

    public DatabaseReference getUserSkills(String uid){
            return databaseRef.child("user-skills").child(uid);
    }

    public void updateTotalSkill(String uid, int total){
        databaseRef.child("users").child(uid).child("totalSkill").setValue(total);
    }

    public Task<Void> updateSkill(String uid, Skill skill){
        return databaseRef.child("user-skills").child(uid).child(skill.getCode()).setValue(skill);
    }

    public Task<Void> removeSkill(String uid, Skill skill){
        return databaseRef.child("user-skills").child(uid).child(skill.getCode()).removeValue();
    }

    //userskill

    //userprestasi

    public DatabaseReference getUserPrestasi(String uid){
        return databaseRef.child("user-prestasi").child(uid);
    }

    public Task<Void> updatePrestasi(String uid, Prestasi prestasi){
        return databaseRef.child("user-prestasi").child(uid).child(prestasi.getId()).setValue(prestasi);
    }

    public Task<Void> removePrestasi(String uid, Prestasi prestasi){
        return databaseRef.child("user-prestasi").child(uid).child(prestasi.getId()).removeValue();
    }

    //userprestasi

    //userpengalaman

    public DatabaseReference getUserPengalaman(String uid){
        return databaseRef.child("user-pengalaman").child(uid);
    }

    public Task<Void> updatePengalaman(String uid, Pengalaman pengalaman){
        return databaseRef.child("user-pengalaman").child(uid).child(pengalaman.getId()).setValue(pengalaman);
    }

    public Task<Void> removePengalaman(String uid, Pengalaman pengalaman){
        return databaseRef.child("user-pengalaman").child(uid).child(pengalaman.getId()).removeValue();
    }

    //userprestasi


    //userschedule

    public DatabaseReference getUserSchedule(String uid){
        return databaseRef.child("user-shedules").child(uid);
    }

    public void updateSchedule(String uid, WeekViewEvent weekViewEvent){
        databaseRef.child("user-schedules").child(uid).child(Long.toString(weekViewEvent.getId())).setValue(weekViewEvent);
    }

    //userschedule

    //Userlocation
    public DatabaseReference getUserLocation(String uid){
        return databaseRef.child("user-location").child(uid);
    }


    //update price
    public  void updateUserPrice(String uid, int price){
        databaseRef.child("users").child(uid).child("startFrom").setValue(price);
    }

    //update price

    public DatabaseReference getUserPayment(String uid){
        return databaseRef.child("partner-payment").child(uid);
    }

    public void updateUserPayment(PartnerPayment partnerPayment){
        databaseRef.child("partner-payment").child(partnerPayment.getUid()).setValue(partnerPayment);
    }

    public void updateStatus(String uid, boolean status){
        databaseRef.child("users").child(uid).child("active").setValue(status);
    }

}
