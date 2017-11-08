package com.lesgood.guru.ui.skill;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;

import com.lesgood.guru.data.model.Skill;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.add_skill.AddSkillActivity;
import com.lesgood.guru.ui.brief.BriefActivity;
import com.lesgood.guru.ui.brief.BriefPresenter;
import com.lesgood.guru.ui.main.MainActivity;

import com.lesgood.guru.util.Const;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by Agus on 5/10/17.
 */

public class SkillActivity extends BaseActivity {

    private static int REQUEST_CODE_SKIL = 1059;

    @BindString(R.string.error_field_required)
    String errRequired;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rv_skill)
    RecyclerView rvItems;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Inject
    User user;

    @Inject
    SkillPresenter presenter;

    @Inject
    SkillAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        ButterKnife.bind(this);

        toolbar.setTitle("Kemampuan Mengajar");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showItems();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new SkillActivityModule((this)))
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clearList();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.clearList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResultF();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            setResultF();
        }

        return super.onOptionsItemSelected(item);
    }


    public void showLoading(boolean show){
        if(show){
            viewProgress.setVisibility(View.VISIBLE);
        }else{
            viewProgress.setVisibility(View.GONE);
        }
    }

    private void setResultF(){
        Intent intent = new Intent(this, MainActivity.class);
        int totalSkill = adapter.getItemCount();
        int startForm = adapter.getMinRate();
        intent.putExtra(Const.EXTRA_TOTAL_SKILL, totalSkill);
        intent.putExtra(Const.EXTRA_START_FROM, startForm);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void showAddedItem(Skill item) {
        adapter.onItemAdded(item);
        adapter.notifyDataSetChanged();
    }

    public void showChangedItem(Skill item) {
        adapter.onItemChanged(item);
        adapter.notifyDataSetChanged();
    }

    public void showRemovedItem(Skill item){
        adapter.onItemRemoved(item);
        adapter.notifyDataSetChanged();
    }

    public void showItems() {
        rvItems.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
    }

    public void showDeleteItem(Skill skill){
        showLoading(true);
        presenter.deleteSkill(skill);
    }

    @OnClick(R.id.fab_add_skill)
    void showAddSkill(){
        Skill skill = new Skill();
        skill.setPrice1(0);
        skill.setPrice2(0);
        skill.setPrice3(0);
        skill.setPrice4(0);
        skill.setPrice5(0);

        startAddSkill(skill);
    }

    public void startAddSkill(Skill skill){
        AddSkillActivity.startWithData(this, skill);
    }

}
