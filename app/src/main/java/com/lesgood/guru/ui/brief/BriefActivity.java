package com.lesgood.guru.ui.brief;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.edit_profile.EditProfileActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;


/**
 * Created by Agus on 3/6/17.
 */

public class BriefActivity extends BaseActivity {

    private static int REQUEST_CODE_ADD_BRIEF = 1054;

    @BindString(R.string.error_field_required)
    String errRequired;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.editor)
    RichEditor editor;

    @Inject
    User user;


    @Inject
    BriefPresenter presenter;

    String brief;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            brief = bundle.getString("brief");
            init();
        }

        toolbar.setTitle("Ceritakan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editor.setEditorHeight(200);
        editor.setEditorFontSize(22);
        editor.setEditorFontColor(colorGrey800);
        //mEditor.setEditorBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        editor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        editor.setPlaceholder("Ceritakan seluruh kebutuhanmu...");

    }

    private void init(){
        if (brief !=null) editor.setHtml(brief);

    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new BriefActivityModule(this))
                .inject(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setResultF(){
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("brief", brief);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading(false);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_brief, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
//            if (!isSaved){
//                showDialogSave();
//            }else{
//                super.onBackPressed();
//                if (forResult) setRes();
//            }
        }

        if (id == R.id.action_save){
            showLoading(true);
            validate();
        }



        return super.onOptionsItemSelected(item);
    }

    public void validate(){
        brief = editor.getHtml();

        if (brief.length() <= 225){
            showLoading(false);
            String title = "Terlalu Pendek";
            String desc = "Ceritamu terlalu pendek, ceritakan dengan jelas dan lengkap (min 225 karakter)";
            int icon = R.drawable.ic_attention_24_red;

            showAlertDialog(title, desc, icon);
        }else{
            setResultF();
        }
    }

    private void showAlertDialog(String title, String desc, int icon){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(desc)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                    }
                })
                .setIcon(icon)
                .show();
    }

    public void showLoading(boolean show){
        if (show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.action_h1)
    void showH1(){
        editor.setHeading(1);
    }

    @OnClick(R.id.action_h2)
    void showH2(){
        editor.setHeading(2);
    }

    @OnClick(R.id.action_bold)
    void showBold(){
        editor.setBold();
    }

    @OnClick(R.id.action_Italic)
    void showItalic(){
        editor.setItalic();
    }

    @OnClick(R.id.action_bulleted)
    void showBulleted(){
        editor.setBullets();
    }

    @OnClick(R.id.action_unordered_numbered)
    void showNumbered(){
        editor.setNumbers();
    }

    @OnClick(R.id.action_insert_link)
    void showInserLink(){
        showAddLinkDialog();
    }

    public void showDialogSave(){

    }


    private void showAddLinkDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_link);

        final EditText inputURL = (EditText) dialog.findViewById(R.id.input_link);
        final EditText inputAlias = (EditText) dialog.findViewById(R.id.input_alias);
        final Button btnSave = (Button) dialog.findViewById(R.id.btn_positif);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_negatif);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputURL.setError(null);
                inputAlias.setError(null);

                String url = inputURL.getText().toString();
                String alias = inputAlias.getText().toString();

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(url)){
                    inputURL.setError(errRequired);
                    cancel = true;
                    focusView = inputURL;
                }

                if (TextUtils.isEmpty(alias)){
                    inputAlias.setError(errRequired);
                    cancel = true;
                    focusView = inputAlias;
                }

                if (cancel){
                    focusView.requestFocus();
                }else{
                    editor.insertLink(url, alias);
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
