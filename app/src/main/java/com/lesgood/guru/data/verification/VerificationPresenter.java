package com.lesgood.guru.data.verification;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lesgood.guru.base.BasePresenter;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.remote.FirebaseImageService;
import com.lesgood.guru.data.remote.UserService;
import com.lesgood.guru.util.AppUtils;

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
        return firebaseImageService.getUserProofKtpThumb(uid);
    }

    public StorageReference getSertifikatRef(String uid){
        return firebaseImageService.getUserProofSertifikatThumb(uid);
    }

    public StorageReference getIjazah(String uid){
        return firebaseImageService.getUserProofIjazahThumb(uid);
    }

    public StorageReference getKTMRef(String uid){
        return firebaseImageService.getUserProofKTMThumb(uid);
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

    public void uploadKtm(final User user, byte[] data, final Uri uri){
        StorageReference ref = firebaseImageService.getUserProofKTM(user.getUid());
        uploadimg(user, data, uri, ref);
    }

    public void uploadimg(final User user, byte[] data, final Uri uri, StorageReference ref){
        UploadTask uploadTask = ref.putFile(uri);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            System.out.print(exception);
            AppUtils.showToast(activity.getApplicationContext(),exception.getMessage());
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            activity.successUploadImage(downloadUrl.toString());
            AppUtils.showToast(activity.getApplicationContext(),taskSnapshot.toString());
        });
    }
}
