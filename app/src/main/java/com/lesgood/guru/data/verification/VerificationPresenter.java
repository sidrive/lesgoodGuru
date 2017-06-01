package com.lesgood.guru.data.verification;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseImageService;
import com.lesgood.guru.data.remote.UserService;

/**
 * Created by Agus on 5/31/17.
 */

public class VerificationPresenter implements BasePresenter {

    VerificationActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;

    public VerificationPresenter(VerificationActivity activity, UserService userService, FirebaseImageService firebaseImageService){
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

    public StorageReference getKtpRef(String uid){
        return firebaseImageService.getUserProofKtp(uid);
    }

    public StorageReference getSertifikatRef(String uid){
        return firebaseImageService.getUserProofSertifikat(uid);
    }

    public StorageReference getIjazah(String uid){
        return firebaseImageService.getUserProofIjazah(uid);
    }

    public void uploadKtp(final User user, byte[] data, final Uri uri){
        StorageReference ref = firebaseImageService.getUserProofKtp(user.getUid());
        uploadimg(user, data, uri, ref);
    }

    public void uploadSertifikat(final User user, byte[] data, final Uri uri){
        StorageReference ref = firebaseImageService.getUserProofSertifikat(user.getUid());
        uploadimg(user, data, uri, ref);
    }

    public void uploadIjazah(final User user, byte[] data, final Uri uri){
        StorageReference ref = firebaseImageService.getUserProofIjazah(user.getUid());
        uploadimg(user, data, uri, ref);
    }


    public void uploadimg(final User user, byte[] data, final Uri uri, StorageReference ref){


        UploadTask uploadTask = ref.putFile(uri);
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
