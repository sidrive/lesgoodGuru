package com.lesgood.guru.ui.add_skill;

import static java.util.Objects.isNull;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.OnTextChanged;
import butterknife.OnTextChanged.Callback;
import com.google.android.gms.maps.model.LatLng;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.Category;
import com.lesgood.guru.data.model.Skill;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.skill.SkillActivityModule;
import com.lesgood.guru.ui.skill.SkillAdapter;
import com.lesgood.guru.ui.skill.SkillPresenter;
import com.lesgood.guru.util.InputFilterMinMax;
import com.lesgood.guru.util.Utils;

import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 5/9/17.
 */

public class AddSkillActivity extends BaseActivity {
    public static String EXTRA_EDIT = "edit";
    @BindString(R.string.error_field_required)
    String errRequired;

    @BindString(R.string.error_field_pengisian)
    String errRequiredMinimal;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Bind(R.id.txt_category)
    TextView txtCategory;

    @Bind(R.id.txt_skill)
    TextView txtSkill;

    @Bind(R.id.txt_level)
    TextView txtLevel;

    @Bind(R.id.input_price_1)
    EditText inputPrice1;

    @Bind(R.id.input_price_2)
    EditText inputPrice2;

    @Bind(R.id.input_price_3)
    EditText inputPrice3;

    @Bind(R.id.input_price_4)
    EditText inputPrice4;

    @Bind(R.id.input_price_5)
    EditText inputPrice5;

    @Bind(R.id.input_how)
    EditText inputHow;

    @Bind(R.id.input_fasilitas)
    EditText inputFasility;

    @Bind(R.id.paket20)
    CheckBox paket20;

    @Bind(R.id.paket30)
    CheckBox paket30;

    @Inject
    User user;

    @Inject
    AddSkillPresenter presenter;

    @Inject
    Skill skill;

    CharSequence[] categories;
    CharSequence[] subcategories;
    CharSequence[] levels;

    List<Category> listCategories;
    List<Category> listSubcategories;
    List<Category> listLevels;

    int categoryVal;
    int subcategoryVal;
    int levelVal;


    public static void startWithData(BaseActivity activity, Skill skill){
        BaseApplication.get(activity).createSkillComponent(skill);
        Intent intent = new Intent(activity, AddSkillActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        ButterKnife.bind(this);

        toolbar.setTitle("Tambah Kemampuan Mengajar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

    }

    public void init(){
        if (skill.getPrice1() != 0) inputPrice1.setText(Integer.toString(skill.getPrice1()));
        if (skill.getPrice2() != 0) inputPrice2.setText(Integer.toString(skill.getPrice2()));
        if (skill.getPrice3() != 0) inputPrice3.setText(Integer.toString(skill.getPrice3()));
        if (skill.getPrice4() != 0) inputPrice4.setText(Integer.toString(skill.getPrice4()));
        if (skill.getPrice5() != 0) inputPrice5.setText(Integer.toString(skill.getPrice5()));
        if (skill.getHow() != null) inputHow.setText(skill.getHow());
        if (skill.getFasility() != null) inputFasility.setText(skill.getFasility());
        if (skill.getSkill() != null) initSubcategory();
        if (skill.getLevel() != null) initLevel();

        paket20.setChecked(skill.isHave20());
        paket30.setChecked(skill.isHave30());
    }

    public void initSubcategory(){
        txtSkill.setText(skill.getSkill());
        if (listSubcategories != null){
            for (int i=0;i<listSubcategories.size();i++){
                if (listSubcategories.get(i).getName().equalsIgnoreCase(skill.getSkill())){
                    subcategoryVal = i;
                }
            }
        }
    }

    public void initLevel(){
        txtLevel.setText(skill.getLevel());
        if (listLevels != null){
            for (int i=0;i<listLevels.size();i++){
                if (listLevels.get(i).getName().equalsIgnoreCase(skill.getLevel())){
                    levelVal = i;
                }
            }
        }
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getSkillComponent()
                .plus(new AddSkillActivityModule((this)))
                .inject(this);
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

        if (id == android.R.id.home){
            finish();
        }

        if (id == R.id.menu_done){

            View focusView1 = null;
            boolean cancel = false;

            if (skill.getSkill() == null){
                cancel = true;
                Toast.makeText(this, "Pilih Materi", Toast.LENGTH_SHORT).show();
            } else if (skill.getLevel() == null){
                cancel = true;
                Toast.makeText(this, "Pilih Tingkat Mengajar", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(String.valueOf(inputPrice1.getText()))) {
                inputPrice1.setError(errRequiredMinimal);
                cancel = true;
                focusView1 = inputPrice1;
                showLoading(false);
                focusView1.requestFocus();
            }else if (TextUtils.isEmpty(String.valueOf(inputPrice2.getText()))) {
                inputPrice2.setError(errRequiredMinimal);
                cancel = true;
                focusView1 = inputPrice2;
                showLoading(false);
                focusView1.requestFocus();
            }else if (TextUtils.isEmpty(String.valueOf(inputPrice3.getText()))) {
                inputPrice3.setError(errRequiredMinimal);
                cancel = true;
                focusView1 = inputPrice3;
                showLoading(false);
                focusView1.requestFocus();
            }else if (TextUtils.isEmpty(String.valueOf(inputPrice4.getText()))) {
                inputPrice4.setError(errRequiredMinimal);
                cancel = true;
                focusView1 = inputPrice4;
                showLoading(false);
                focusView1.requestFocus();
            }else if (TextUtils.isEmpty(String.valueOf(inputPrice5.getText()))) {
                inputPrice5.setError(errRequiredMinimal);
                cancel = true;
                focusView1 = inputPrice5;
                showLoading(false);
                focusView1.requestFocus();
            }else {
                showLoading(true);
                validate();
            }
        }

        return super.onOptionsItemSelected(item);
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
        BaseApplication.get(this).releaseSkillComponent();
    }


    public void initCategories(List<Category> listCategories){
        this.listCategories = listCategories;
        categories = new CharSequence[listCategories.size()];
        for (int i=0;i<listCategories.size();i++){
            categories[i] = listCategories.get(i).getName();
        }

    }

    public void initSubcategories(List<Category> listSubcategories){
        this.listSubcategories = listSubcategories;
        subcategories = new CharSequence[listSubcategories.size()];
        for (int i=0;i<listSubcategories.size();i++){
            subcategories[i] = listSubcategories.get(i).getName();
        }

    }

    public void initLevel(List<Category> listLevels){
        this.listLevels = listLevels;
        levels = new CharSequence[listLevels.size()];
        for (int i=0;i<listLevels.size();i++){
            levels[i] = listLevels.get(i).getName();
        }

    }


    public void showLoading(boolean show){
        if(show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.lin_category)
    void showCategories(){
        showDialogSelectCategory();
    }

    @OnClick(R.id.lin_subcategory)
    void showSubCategories(){
        showDialogSelectSubCategory();
    }

    @OnClick(R.id.lin_level)
    void showLevel(){
        showDialogSelectLevel();
    }

    private void showDialogSelectCategory() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pilih Kategori");
        alert.setSingleChoiceItems(categories, categoryVal, (dialog, which) -> {
            handleSelectCategory(which);
            dialog.dismiss();

        });
        alert.show();
    }

    private void handleSelectCategory(int pos){
        categoryVal = pos;
        String category = categories[pos].toString();
        txtCategory.setText(category);
        presenter.getSubCategories(listCategories.get(pos).getId());
    }


    private void showDialogSelectSubCategory() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pilih Materi");
        alert.setSingleChoiceItems(subcategories, subcategoryVal, (dialog, which) -> {
            handleSelectSubCategory(which);
            dialog.dismiss();

        });
        alert.show();
    }

    private void handleSelectSubCategory(int pos){
        subcategoryVal = pos;
        String subcategory = subcategories[pos].toString();
        txtSkill.setText(subcategory);
        skill.setSkill(subcategory);
        presenter.getLevels(listSubcategories.get(pos).getLevel());
    }

    private void showDialogSelectLevel() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Pilih Tingkat");
        alert.setSingleChoiceItems(levels, levelVal, (dialog, which) -> {
            handleSelectLevel(which);
            dialog.dismiss();
        });
        alert.show();
    }

    private void handleSelectLevel(int pos){
        levelVal = pos;
        String level = levels[pos].toString();
        txtLevel.setText(level);
        skill.setLevel(level);
    }

    public void validate(){
        inputPrice1.setError(null);
        inputPrice2.setError(null);
        inputPrice3.setError(null);
        inputPrice4.setError(null);
        inputPrice5.setError(null);
        boolean cancel = false;
        View focusView = null;

            skill.setPrice1(Integer.valueOf(inputPrice1.getText().toString()));
            skill.setPrice2(Integer.valueOf(inputPrice2.getText().toString()));
            skill.setPrice3(Integer.valueOf(inputPrice3.getText().toString()));
            skill.setPrice4(Integer.valueOf(inputPrice4.getText().toString()));
            skill.setPrice5(Integer.valueOf(inputPrice5.getText().toString()));

      String how = inputHow.getText().toString();
      String fasility = inputFasility.getText().toString();
      boolean havepaket20 = paket20.isChecked();
      boolean havepaket30 = paket30.isChecked();

      skill.setHave20(havepaket20);
      skill.setHave30(havepaket30);


        if (skill.getPrice1() < 40000){
            inputPrice1.setError(errRequiredMinimal);
            cancel = true;
            focusView = inputPrice1;
            showLoading(false);
        }

        if (skill.getPrice2() <= 40000){
            inputPrice2.setError(errRequiredMinimal);
            cancel = true;
            focusView = inputPrice2;
            showLoading(false);
        }

        if (skill.getPrice3() <= 40000){
            inputPrice3.setError(errRequiredMinimal);
            cancel = true;
            focusView = inputPrice3;
            showLoading(false);
        }

        if (skill.getPrice4() <= 40000){
        inputPrice4.setError(errRequiredMinimal);
        cancel = true;
        focusView = inputPrice4;
        showLoading(false);
        }

        if (skill.getPrice5() <= 40000){
            inputPrice5.setError(errRequiredMinimal);
            cancel = true;
            focusView = inputPrice5;
            showLoading(false);
        }

        if (cancel){
            focusView.requestFocus();
        }else{
            showLoading(true);
            String idSkill = listSubcategories.get(subcategoryVal).getId();
            String idLevel = listLevels.get(levelVal).getId();
            String code = idSkill+idLevel;

            if (!TextUtils.isEmpty(how)) skill.setHow(how);
            if (!TextUtils.isEmpty(fasility)) skill.setFasility(fasility);

            skill.setCode(code);
            presenter.updateSkill(user.getUid(), skill);
        }

    }
    @OnTextChanged(value = R.id.input_price_1,callback = Callback.AFTER_TEXT_CHANGED)void setPrice(CharSequence s){
        Log.e("setPrice", "AddSkillActivity" + s);
        int minprice = 40000;
        if (s.length()>3){
            if (Integer.parseInt(s.toString()) < minprice){
                inputPrice1.setError(errRequiredMinimal);
            }else {
                inputPrice2.setText(""+minprice*2);
                inputPrice3.setText(""+minprice*3);
                inputPrice4.setText(""+minprice*4);
                inputPrice5.setText(""+minprice*5);

            }
        }
    }
    public void successUpdateSkill(){
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
        finish();
    }
}
