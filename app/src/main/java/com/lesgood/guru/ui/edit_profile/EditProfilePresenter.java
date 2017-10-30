package com.lesgood.guru.ui.edit_profile;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseImageService;
import com.lesgood.guru.data.remote.UserService;


/**
 * Created by Agus on 4/20/17.
 */

public class EditProfilePresenter implements BasePresenter {
    EditProfileActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;

    public EditProfilePresenter(EditProfileActivity activity, UserService userService, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void updateProfile(final User user){
        userService.updateUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                activity.successUpdateProfile(user);
            }
        });
    }


    public void getPayment(String uid){
        userService.getUserPayment(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PartnerPayment partnerPayment = dataSnapshot.getValue(PartnerPayment.class);
                if (partnerPayment != null) activity.initPayment(partnerPayment);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void updatePayment(PartnerPayment partnerPayment){
        userService.updateUserPayment(partnerPayment);
    }

    public void uploadAvatar(final User user, byte[] data, final Uri uri){
        StorageReference avatarPartnerRef = firebaseImageService.getUserImageRefOriginal(user.getUid());

        UploadTask uploadTask = avatarPartnerRef.putFile(uri);
// Register observers to listen for when the download is done or if it fails


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.print(exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                activity.successUploadImage(downloadUrl.toString());

            }
        });
    }
}
