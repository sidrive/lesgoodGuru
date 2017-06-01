package com.lesgood.guru.ui.payment_detail;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.PartnerPayment;
import com.lesgood.guru.data.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 2/23/17.
 */

public class PaymentDetailActivity extends BaseActivity {

    @BindString(R.string.error_field_required)
    String strErrRequired;

    @BindColor(R.color.colorBlack)
    int colorBlack;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.input_select_bank)
    EditText inputSelectBank;

    @Bind(R.id.input_account_number)
    EditText inputAccNumber;

    @Bind(R.id.input_account_name)
    EditText inputAccName;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    User user;

    @Inject
    PaymentDetailPresenter presenter;

    private int bankVal;

    public static void startWithUser(Activity activity, final User user) {
        Intent intent = new Intent(activity, PaymentDetailActivity.class);
        BaseApplication.get(activity).createUserComponent(user);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new PaymentDetailActivityModule(this))
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void showDialogSelectBank(){
        final CharSequence[] banks = getResources().getStringArray(R.array.banks_array);
        final CharSequence[] bank_ids = getResources().getStringArray(R.array.banks_id_array);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Select Bank");
        alert.setSingleChoiceItems(banks,bankVal, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String whichIs = banks[which].toString();
                inputSelectBank.setText(whichIs);

                inputSelectBank.setTextColor(colorBlack);

                bankVal = Integer.valueOf(bank_ids[which].toString());

                dialog.dismiss();

            }
        });
        alert.show();
    }

    public void initPayment(PartnerPayment partnerPayment){
        bankVal = partnerPayment.getBankCode();
        inputSelectBank.setText(partnerPayment.getBank());
        inputAccName.setText(partnerPayment.getName());
        inputAccNumber.setText(partnerPayment.getAccount());
    }

    public void showSuccessSaved(){
        setLoadingProgress(false);
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
    }

    public void setLoadingProgress(boolean show){
        if (show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.btn_save)
    void save(){
        validate();
    }

    @OnClick(R.id.input_select_bank)
    void showBanks(){
        showDialogSelectBank();
    }

    public void validate(){
        inputSelectBank.setError(null);
        inputAccName.setError(null);
        inputAccNumber.setError(null);

        String selectBank = inputSelectBank.getText().toString();
        String accName = inputAccName.getText().toString();
        String accNumber = inputAccNumber.getText().toString();

        boolean cancel = false;
        View viewFocus = null;

        if (TextUtils.isEmpty(selectBank)){
            viewFocus = inputSelectBank;
            inputSelectBank.setError(strErrRequired);
            cancel = true;
        }

        if (TextUtils.isEmpty(accName)){
            viewFocus = inputAccName;
            inputAccName.setError(strErrRequired);
            cancel = true;
        }

        if (TextUtils.isEmpty(accNumber)){
            viewFocus = inputAccNumber;
            inputAccNumber.setError(strErrRequired);
            cancel = true;
        }

        if (cancel){
            viewFocus.requestFocus();
        }else{
            setLoadingProgress(true);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String updateAt = dateFormat.format(date);
            PartnerPayment partnerPayment = new PartnerPayment(user.getUid(), selectBank, bankVal, accNumber, accName, updateAt);

            presenter.save(partnerPayment);
        }
    }
}
