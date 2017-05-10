package com.lesgood.guru.ui.add_skill;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindString(R.string.error_field_required)
    String errRequired;

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

    @Bind(R.id.input_price)
    EditText inputPrice;

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
            showLoading(true);
            validate();

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
        alert.setTitle("Select Province");
        alert.setSingleChoiceItems(categories, categoryVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleSelectCategory(which);
                dialog.dismiss();

            }
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
        alert.setTitle("Select Province");
        alert.setSingleChoiceItems(subcategories, subcategoryVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleSelectSubCategory(which);
                dialog.dismiss();

            }
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
        alert.setTitle("Select Province");
        alert.setSingleChoiceItems(levels, levelVal, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleSelectLevel(which);
                dialog.dismiss();

            }
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
        inputPrice.setError(null);
        boolean cancel = false;
        View focusView = null;

        skill.setPrice(Integer.valueOf(inputPrice.getText().toString()));

        if (TextUtils.isEmpty(skill.getSkill())){
            cancel = true;
            Toast.makeText(this, "Pilih Pelajaran", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(skill.getLevel())){
            cancel = true;
            Toast.makeText(this, "Pilih Tingkat Mengajar", Toast.LENGTH_SHORT).show();
        }

        if (skill.getPrice() == 0){
            inputPrice.setError(errRequired);
            cancel = true;
            focusView = inputPrice;
        }

        if (cancel){
            focusView.requestFocus();
        }else{
            showLoading(true);
            presenter.updateSkill(user.getUid(), skill);
        }

    }

    public void successUpdateSkill(){
        Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
        finish();
    }
}
