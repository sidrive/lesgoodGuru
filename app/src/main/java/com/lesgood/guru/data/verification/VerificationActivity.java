package com.lesgood.guru.data.verification;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.dialog.DialogUploadOption;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Agus on 5/31/17.
 */

public class VerificationActivity extends BaseActivity implements DialogUploadOption.OnDialogUploadOptionClickListener,
        EasyPermissions.PermissionCallbacks{

    private static final String TAG = "VerificationStepActivity";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    public static Uri mCapturedImageURI;

    @BindString(R.string.error_field_required)
    String strErrRequired;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.txt_foto_ktp)
    TextView txtKtp;

    @Bind(R.id.img_ktp)
    ImageView imgKtp;

    @Bind(R.id.txt_foto_keahlian)
    TextView txtKeahlian;

    @Bind(R.id.img_foto_keahlian)
    ImageView imgKeahlian;

    @Bind(R.id.txt_foto_ijazah)
    TextView txtIjazah;

    @Bind(R.id.img_foto_ijazah)
    ImageView imgIjazah;

    @Inject
    VerificationPresenter presenter;

    @Inject
    User user;

    byte[] imgSmall;
    Uri imgOriginal;

    String type;

    public static void startWithUser(BaseActivity activity, final User user) {
        Intent intent = new Intent(activity, VerificationActivity.class);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        if(mCapturedImageURI != null)
            savedInstanceState.putString("mUriKey", mCapturedImageURI.toString());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        mCapturedImageURI = Uri.parse(savedInstanceState.getString("mUriKey"));
        Log.d("Restore state", "mCapturedImageURI = "+mCapturedImageURI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Verifikasi Data");

        init();

    }

    public void init(){
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(presenter.getKtpRef(user.getUid()))
                .into(imgKtp);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(presenter.getSertifikatRef(user.getUid()))
                .into(imgKeahlian);

        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(presenter.getIjazah(user.getUid()))
                .into(imgIjazah);
    }

    @Override
    public void onBackPressed() {
        if (viewProgress.getVisibility() == View.GONE) super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getUserComponent()
                .plus(new VerificationActivityModule(this))
                .inject(this);

    }

    public void showLoading(boolean show){
        if (show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUST_CODE_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = mCapturedImageURI;
                handleCrop(uri);
            }
        }

        if (requestCode == REQUST_CODE_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                handleCrop(uri);

            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = result.getUri();
                imgOriginal = uri;

                try {
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    encodeBitmapAndSaveToFirebase(bitmap2);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
    }


    private void handleCrop(Uri uri){
        CropImage.activity(uri)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        imgSmall = baos.toByteArray();

        showLoading(true);
        if (type.equalsIgnoreCase("ktp")){
            imgKtp.setImageBitmap(bitmap);
            presenter.uploadKtp(user, imgSmall, imgOriginal);
        }else if (type.equalsIgnoreCase("keahlian")){
            imgKeahlian.setImageBitmap(bitmap);
            presenter.uploadSertifikat(user, imgSmall, imgOriginal);
        }else{
            imgIjazah.setImageBitmap(bitmap);
            presenter.uploadIjazah(user, imgSmall, imgOriginal);
        }
    }


    /// ...ON CLICK///

    @OnClick(R.id.txt_foto_ktp)
    void uploadktp(){
        showDialog();
        type = "ktp";
    }

    @OnClick(R.id.txt_foto_keahlian)
    void uploadkeahlian(){
        showDialog();
        type = "keahlian";
    }

    @OnClick(R.id.txt_foto_ijazah)
    void uploadijazah(){
        showDialog();
        type = "ijazah";
    }

    public void showDialog(){
        DialogUploadOption dialogUploadOption = new DialogUploadOption(this);
        dialogUploadOption.show();
    }

    /// ...ON CLICK///

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public void onLaunchCamera() {
        Intent intent;
        String fileName = nextSessionId();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        mCapturedImageURI = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, REQUST_CODE_CAMERA);
        }
    }

    public void onLaunchGallery(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                "content://media/internal/images/media"));
        intent.setAction(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");
        startActivityForResult(intent, REQUST_CODE_GALLERY);
    }

    public void successUploadImage(String url){
        showLoading(false);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();

    }

    // permission

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onLaunchCamera();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_CAMERA_PERM, perms);
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void galleryTask() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onLaunchGallery();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_CAMERA_PERM, perms);
        }
    }

    @Override
    public void onGalleryClicked(Dialog dialog) {
        galleryTask();
        dialog.dismiss();
    }

    @Override
    public void onCameraClicked(Dialog dialog) {
        cameraTask();
        dialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }



}
