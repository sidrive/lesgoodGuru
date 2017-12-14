package com.lesgood.guru.ui.edit_profile;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.data.verification.VerificationActivity;
import com.lesgood.guru.ui.dialog.DialogUploadOption;
import com.lesgood.guru.ui.dialog.DialogUploadOption.OnDialogUploadOptionClickListener;
import com.lesgood.guru.ui.intro.IntroActivity;
import com.lesgood.guru.ui.main.MainActivity;
import com.lesgood.guru.util.Const;
import com.lesgood.guru.util.DateFormatter;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImage.ActivityResult;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropImageView.CropShape;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import de.hdodenhof.circleimageview.CircleImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.inject.Inject;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

/**
 * Created by Agus on 4/20/17.
 */

public class EditProfileActivity extends BaseActivity implements OnDateSetListener,
    OnDialogUploadOptionClickListener, PermissionCallbacks {

  private static final String TAG = "EditProfileActivity";
  public static final int REQUST_CODE_CAMERA = 1002;
  public static final int REQUST_CODE_GALLERY = 1001;
  private static final int RC_CAMERA_PERM = 205;
  public static Uri mCapturedImageURI;

  @BindString(R.string.error_field_required)
  String strErrRequired;
  @BindColor(R.color.colorBlack)
  int colorBlack;
  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.view_progress)
  LinearLayout viewProgress;

  @Bind(R.id.input_name)
  EditText inputName;

  @Bind(R.id.layout_input_birthday)
  TextInputLayout inputLayoutBirthday;

  @Bind(R.id.layout_input_gender)
  TextInputLayout inputLayoutGender;

  @Bind(R.id.input_birthday)
  EditText inputBirthDay;

  @Bind(R.id.input_gender)
  EditText inputGender;

  @Bind(R.id.input_email)
  EditText inputEmail;

  @Bind(R.id.input_phone)
  EditText inputPhone;

  @Bind(R.id.input_instagram)
  EditText inputInstagram;

  @Bind(R.id.input_facebook)
  EditText inputFacebook;

  @Bind(R.id.input_religion)
  EditText inputReligion;

  @Bind(R.id.input_pendidikan)
  EditText inputPendidikan;

  @Bind(R.id.input_prodi)
  EditText inputProdi;

  @Bind(R.id.input_select_bank)
  EditText inputSelectBank;

  @Bind(R.id.input_account_number)
  EditText inputAccNumber;

  @Bind(R.id.input_account_name)
  EditText inputAccName;

  @Bind(R.id.img_avatar)
  CircleImageView imgAvatar;

  @Inject
  EditProfilePresenter presenter;

  @Inject
  User user;

  CharSequence[] charGenders;
  private FirebaseAuth mAuth;
  private FirebaseUser mUser;
  int genderVal = 3;
  long dateBirthDay = 0;

  byte[] imgSmall;
  Uri imgOriginal;

  boolean register = false;

  private int bankVal = 0;

  public static void startWithUser(BaseActivity activity, final User user, boolean register) {
    Intent intent = new Intent(activity, EditProfileActivity.class);
    intent.putExtra("register", register);
    BaseApplication.get(activity).createUserComponent(user);
    activity.startActivity(intent);
  }

  public static void startWithUserIntro(IntroActivity activity, boolean register) {
    Intent intent = new Intent(activity, EditProfileActivity.class);
    intent.putExtra("register", register);
    /*BaseApplication.get(activity).createUserComponent(user);*/
    activity.startActivity(intent);
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    // Save the user's current game state
    if (mCapturedImageURI != null) {
      savedInstanceState.putString("mUriKey", mCapturedImageURI.toString());
    }

    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(savedInstanceState);
  }

  public void onRestoreInstanceState(Bundle savedInstanceState) {
    // Always call the superclass so it can restore the view hierarchy
    super.onRestoreInstanceState(savedInstanceState);

    // Restore state members from saved instance
    mCapturedImageURI = Uri.parse(savedInstanceState.getString("mUriKey"));
    Log.d("Restore state", "mCapturedImageURI = " + mCapturedImageURI);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);
    ButterKnife.bind(this);
    mAuth = FirebaseAuth.getInstance();
    mUser = mAuth.getCurrentUser();
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    setTitle("Ubah Data Profil");

    charGenders = getResources().getStringArray(R.array.list_gender);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      register = extras.getBoolean("register");
    }

    init();
  }

  @Override
  public void onBackPressed() {
    if (viewProgress.getVisibility() == View.GONE) {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.done, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == android.R.id.home) {
      /*finish();*/
      MainActivity.startWithUser(this, user);
    }

    if (id == R.id.menu_done) {
      showLoading(true);
      validate();

    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getUserComponent()
        .plus(new EditProfileActivityModule(this))
        .inject(this);

  }


  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
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
      ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == Activity.RESULT_OK) {
        Uri uri = result.getUri();
        imgOriginal = uri;

        try {
          Bitmap bitmap2 = Media.getBitmap(getContentResolver(), uri);
          imgAvatar.setImageBitmap(bitmap2);
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

  private void init() {
    inputEmail.setText(mUser.getEmail());
    inputEmail.setEnabled(false);
    if (user.getUid() != null) {
      presenter.getPayment(user.getUid());
    }

    if (user.getFull_name() != null) {
      inputName.setText(user.getFull_name());
    }
    if (user.getBirthday() != 0) {
      initBirthDay(user.getBirthday());
    }
    if (user.getGender() != null) {
      initGender(user.getGender());
    }
    /*if (user.getEmail() != null) {
      inputEmail.setText(user.getEmail());
    }*/
    if (user.getPhone() != null) {
      inputPhone.setText(user.getPhone());
    }
    if (user.getInstagram() != null) {
      inputInstagram.setText(user.getInstagram());
    }
    if (user.getFacebook() != null) {
      inputFacebook.setText(user.getFacebook());
    }
    if (user.getPhoto_url() != null) {
      if (!user.getPhoto_url().equals("NOT")) {
        Glide.with(this)
            .load(user.getPhoto_url())
            .placeholder(R.color.colorSoft)
            .dontAnimate()
            .into(imgAvatar);
      }
    }

    if (user.getReligion() != null) {
      inputReligion.setText(user.getReligion());
    }
    if (user.getPendidikan() != null) {
      inputPendidikan.setText(user.getPendidikan());
    }
    if (user.getProdi() != null) {
      inputProdi.setText(user.getProdi());
    }

    if (!register) {
      inputBirthDay.setEnabled(false);
      inputName.setEnabled(false);
      inputEmail.setEnabled(false);
      inputPhone.setEnabled(false);
      inputReligion.setEnabled(false);
    }
  }

  public void initPayment(PartnerPayment partnerPayment) {
    bankVal = partnerPayment.getBankCode();
    inputSelectBank.setText(partnerPayment.getBank());
    inputAccName.setText(partnerPayment.getName());
    inputAccNumber.setText(partnerPayment.getAccount());
  }

  private void initGender(String i) {
    if (i.equalsIgnoreCase("M")) {
      genderVal = 0;
      inputGender.setText("Laki laki");
    }
    if (i.equalsIgnoreCase("F")) {
      genderVal = 1;
      inputGender.setText("Perempuan");
    }

  }

  private void initBirthDay(long timemilis) {
    dateBirthDay = timemilis;
    String date = DateFormatter.getDate(dateBirthDay, "dd MMM yyyy");
    inputBirthDay.setText(date);
  }

  // Onclick list -------------//

  @OnClick(R.id.input_birthday)
  void showBirthDay() {
    showDialogDatePicker();
  }

  @OnClick(R.id.input_gender)
  void showGender() {
    showDialogGender();
  }

  @OnClick(R.id.btn_upload_avatar)
  void showDialogUploadOption() {
    DialogUploadOption dialogUploadOption = new DialogUploadOption(this);
    dialogUploadOption.show();
  }

  // Onclick list -------------//

  private void showDialogDatePicker() {
    Calendar cal = Calendar.getInstance();
    DatePickerDialog dpd = DatePickerDialog.newInstance(
        this,
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    );

    dpd.setTitle("Tanggal Lahir");
    dpd.vibrate(true);
    dpd.dismissOnPause(false);
    dpd.show(getFragmentManager(), "Datepickerdialog");
  }

  private void showDialogGender() {
    final Builder alert = new Builder(this);
    alert.setTitle("Jenis Kelamin");
    alert.setSingleChoiceItems(charGenders, genderVal, new OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        String whichIs = charGenders[which].toString();

        inputGender.setText(whichIs);
        genderVal = which;

        dialog.dismiss();

      }
    });
    alert.show();
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthOfYear, dayOfMonth);
    initBirthDay(calendar.getTimeInMillis());
  }

  @Override
  public void onGalleryClicked(Dialog dialog) {
    galeryTask();
    dialog.dismiss();
  }

  @Override
  public void onCameraClicked(Dialog dialog) {
    cameraTask();
    dialog.dismiss();
  }

  // Handling list -----------------//

  private void handleCrop(Uri uri) {
    CropImage.activity(uri)
        .setCropShape(CropShape.OVAL)
        .setAspectRatio(500, 500)
        .start(this);
  }

  public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.JPEG, 60, baos);
    imgSmall = baos.toByteArray();

  }

  private SecureRandom random = new SecureRandom();

  public String nextSessionId() {
    return new BigInteger(130, random).toString(32);
  }

  public void onLaunchCamera() {
    Intent intent;
    String fileName = nextSessionId();
    ContentValues values = new ContentValues();
    values.put(Media.TITLE, fileName);
    mCapturedImageURI = this.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
    if (intent.resolveActivity(this.getPackageManager()) != null) {
      startActivityForResult(intent, REQUST_CODE_CAMERA);
    }
  }

  public void onLaunchGallery() {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
        "content://media/internal/images/media"));
    intent.setAction(Intent.ACTION_GET_CONTENT);

    intent.setType("image/*");
    startActivityForResult(intent, REQUST_CODE_GALLERY);
  }

  public void successUploadImage(String url) {
    if (url != null) {
      user.setPhoto_url(url);
    }
    presenter.updateProfile(user);

  }

  public void successUpdateProfile(User user) {
    showLoading(false);
    if (register) {
      if (user.isAcceptTOS()) {
        VerificationActivity.startWithUser(this, user);
      } else {
        /*VerificationActivity.startWithUser(this, user);*/
        IntroActivity.startWithUser(this, user);
      }
    } else {
      Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
      BaseApplication.get(this).createUserComponent(user);
      finish();
    }
  }

  public void validate() {
    inputName.setError(null);
    inputEmail.setError(null);
    inputGender.setError(null);
    inputBirthDay.setError(null);
    inputPhone.setError(null);

    String name = inputName.getText().toString();
    String email = inputEmail.getText().toString();
    String gender = "";
    if (genderVal == 0) {
      gender = "M";
    }
    if (genderVal == 1) {
      gender = "F";
    }
    String phone = inputPhone.getText().toString();
    String religion = inputReligion.getText().toString();
    String pendidikan = inputPendidikan.getText().toString();
    String prodi = inputProdi.getText().toString();
    String instagram = inputInstagram.getText().toString();
    String facebook = inputFacebook.getText().toString();

    String selectBank = inputSelectBank.getText().toString();
    String accName = inputAccName.getText().toString();
    String accNumber = inputAccNumber.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if (TextUtils.isEmpty(name)) {
      inputName.setError(strErrRequired);
      focusView = inputName;
      cancel = true;
    }

    if (TextUtils.isEmpty(email)) {
      inputEmail.setError(strErrRequired);
      focusView = inputEmail;
      cancel = true;
    } else {
      if (!isValidEmail(email)) {
        inputEmail.setError("Email not valid");
        focusView = inputEmail;
        cancel = true;

      }
    }

    if (TextUtils.isEmpty(religion)) {
      inputReligion.setError(strErrRequired);
      focusView = inputReligion;
      cancel = true;
    }

    if (TextUtils.isEmpty(pendidikan)) {
      inputPendidikan.setError(strErrRequired);
      focusView = inputPendidikan;
      cancel = true;
    }

    if (TextUtils.isEmpty(prodi)) {
      inputProdi.setError(strErrRequired);
      focusView = inputProdi;
      cancel = true;
    }

    if (!TextUtils.isEmpty(phone)) {
      if (!isValidPhoneNumber(phone)) {
        inputPhone.setError("Phone number not valid");
        focusView = inputPhone;
        cancel = true;
      }
    }

    if (cancel) {
      focusView.requestFocus();
    } else {

      user.setAcceptTOS(true);
      user.setFull_name(name);
      user.setEmail(email);
      user.setReligion(religion);
      user.setPendidikan(pendidikan);
      user.setProdi(prodi);
      user.setUserType(Const.USER_TYPE);
      if (!TextUtils.isEmpty(phone)) {
        user.setPhone(phone);
      }
      if (!TextUtils.isEmpty(instagram)) {
        user.setInstagram(instagram);
      }
      if (!TextUtils.isEmpty(facebook)) {
        user.setFacebook(facebook);
      }
      if (genderVal != 3) {
        user.setGender(gender);
      }
      if (dateBirthDay != 0) {
        user.setBirthday(dateBirthDay);
      }
      if (register) {
        user.setCreatedAt(System.currentTimeMillis());
      }
      if (!register) {
        user.setUpdateAt(System.currentTimeMillis());
      }

      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Date date = new Date();
      String updateAt = dateFormat.format(date);

      if (!TextUtils.isEmpty(selectBank) || bankVal != 0 || !TextUtils.isEmpty(accNumber)
          || !TextUtils.isEmpty(accName)) {
        PartnerPayment partnerPayment = new PartnerPayment(user.getUid(), selectBank, bankVal,
            accNumber, accName, updateAt);
        presenter.updatePayment(partnerPayment);
      }

      if (imgOriginal != null) {
        presenter.uploadAvatar(user, imgSmall, imgOriginal);
      } else {
        presenter.updateProfile(user);
      }

    }

  }

  // Handling list ------------------//

  // permission

  @AfterPermissionGranted(RC_CAMERA_PERM)
  public void cameraTask() {
    String[] perms = {permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE,
        permission.READ_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(this, permission.CAMERA)) {
      // Have permission, do the thing!
      onLaunchCamera();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
          RC_CAMERA_PERM, perms);
    }
  }

  @AfterPermissionGranted(RC_CAMERA_PERM)
  public void galeryTask() {
    String[] perms = {permission.CAMERA, permission.WRITE_EXTERNAL_STORAGE,
        permission.READ_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(this, permission.CAMERA)) {
      // Have permission, do the thing!
      onLaunchGallery();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
          RC_CAMERA_PERM, perms);
    }
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // EasyPermissions handles the request result.
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
    // This will display a dialog directing them to enable the permission in app settings.
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      new AppSettingsDialog.Builder(this).build().show();
    }
  }

  private boolean isValidEmail(CharSequence target) {
    if (target == null) {
      return false;
    } else {
      return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
  }


  private boolean isValidPhoneNumber(CharSequence target) {
    Pattern mPattern = Pattern.compile("^08[0-9]{9,}$");
    if (target == null) {
      return false;
    } else {
      return mPattern.matcher(target).matches();
    }
  }

  @OnClick(R.id.input_select_bank)
  public void showBanks() {
    showDialogSelectBank();
  }

  private void showDialogSelectBank() {
    final CharSequence[] banks = getResources().getStringArray(R.array.banks_array);
    final CharSequence[] bank_ids = getResources().getStringArray(R.array.banks_id_array);
    final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle("Select Bank");
    alert.setSingleChoiceItems(banks,bankVal, (dialog, which) -> {
      String whichIs = banks[which].toString();
      inputSelectBank.setText(whichIs);

      inputSelectBank.setTextColor(colorBlack);

      bankVal = Integer.valueOf(bank_ids[which].toString());

      dialog.dismiss();

    });
    alert.show();
  }
}
