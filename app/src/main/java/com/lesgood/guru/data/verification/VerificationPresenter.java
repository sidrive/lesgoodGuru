package com.lesgood.guru.data.verification;

import android.net.Uri;

import android.util.Log;
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
    public  void getUriKTP(String uid){
        activity.showLoading(true);
        StorageReference reference = firebaseImageService.getUserProofKtpThumb(uid);
        reference.getDownloadUrl().addOnSuccessListener(uri -> {
            activity.setImageKTP(uri);
            activity.showLoading(false);
        }).addOnFailureListener(e -> {
            activity.showLoading(false);
        });
    }
    public  void getUriKTM(String uid){
        activity.showLoading(true);
        StorageReference reference = firebaseImageService.getUserProofKTMThumb(uid);
        reference.getDownloadUrl().addOnSuccessListener(uri -> {
            activity.setImageKTM(uri);
            activity.showLoading(false);
        }).addOnFailureListener(e -> {
            activity.showLoading(false);
        });
    }
    public  void getUriIjazah(String uid){
        activity.showLoading(true);
        StorageReference reference = firebaseImageService.getUserProofIjazahThumb(uid);
        reference.getDownloadUrl().addOnSuccessListener(uri -> {
            activity.setImageIjazah(uri);
            activity.showLoading(false);
        }).addOnFailureListener(e -> {
            activity.showLoading(false);
        });
    }
    public  void getUriSertifikat(String uid){
        activity.showLoading(true);
        StorageReference reference = firebaseImageService.getUserProofSertifikatThumb(uid);
        reference.getDownloadUrl().addOnSuccessListener(uri -> {
            activity.setImageSertifikat(uri);
            activity.showLoading(false);
        }).addOnFailureListener(e -> {
            activity.showLoading(false);
        });
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
            Log.e("uploadimg fail", "VerificationPresenter" + exception.getMessage());
            AppUtils.showToast(activity.getApplicationContext(),exception.getMessage());
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            activity.showLoading(false);
            /*AppUtils.showToast(activity.getApplicationContext(),taskSnapshot.toString());*/
            Log.e("uploadimg success", "VerificationPresenter" + taskSnapshot.getError());
        });

    }
}
