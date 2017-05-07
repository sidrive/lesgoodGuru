package com.lesgood.guru.data.remote;

import android.app.Application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * Created by Agus on 2/25/17.
 */

public class FirebaseImageService {
    private Application application;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public FirebaseImageService(Application application){
        this.application = application;
        this.firebaseStorage = FirebaseStorage.getInstance();
        this.storageReference = firebaseStorage.getReference();
    }

    public StorageReference getUserImageRefOriginal(String uid){
        StorageReference avatarRef = storageReference.child("users/"+uid+"/profile.jpg");
        return avatarRef;
    }

    public StorageReference getImageRefThumb(String uid){
        StorageReference avatarRefThumb = storageReference.child("users/"+uid+"/thumb_profile.jpg");
        return avatarRefThumb;
    }

    public StorageReference getUserProofKtp(String uid){
        StorageReference imgRef = storageReference.child("users/ktp/"+uid+"/profile.jpg");
        return imgRef;
    }

    public StorageReference getUserProofKtpThumb(String uid){
        StorageReference imgRef = storageReference.child("users/ktp/"+uid+"/thumb_profile.jpg");
        return imgRef;
    }

    public StorageReference getCampaignCover(String cid){
        StorageReference imgRef = storageReference.child("campaigns/"+cid+"/cover.jpg");
        return imgRef;
    }

    public StorageReference getCampaignCoverThumb(String cid){
        StorageReference imgRef = storageReference.child("campaigns/"+cid+"/thumb_cover.jpg");
        return imgRef;
    }
}
