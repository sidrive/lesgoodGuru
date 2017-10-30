package com.lesgood.guru.ui.pengalaman;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;
import com.lesgood.guru.base.BaseApplication;
import com.lesgood.guru.data.model.Pengalaman;
import com.lesgood.guru.data.model.Prestasi;
import com.lesgood.guru.data.model.User;
import com.lesgood.guru.ui.main.MainActivity;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 6/1/17.
 */

public class PengalamanActivity extends BaseActivity {
    @BindString(R.string.error_field_required)
    String errRequired;

    @BindColor(R.color.colorGrey800)
    int colorGrey800;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rv_items)
    RecyclerView rvItems;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    @Inject
    User user;

    @Inject
    PengalamanPresenter presenter;

    @Inject
    PengalamanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestasi);
        ButterKnife.bind(this);

        toolbar.setTitle("Pengalaman");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showItems();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .plus(new PengalamanActivityModule((this)))
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
        int totalPrestasi = adapter.getItemCount();
        intent.putExtra("totalPrestasi", totalPrestasi);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void showAddedItem(Pengalaman item) {
        adapter.onItemAdded(item);
    }

    public void showChangedItem(Pengalaman item) {
        adapter.onItemChanged(item);
    }

    public void showRemovedItem(Pengalaman item){
        adapter.onItemRemoved(item);
    }

    public void showItems() {
        rvItems.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
    }

    public void showDeleteItem(Pengalaman item){
        showLoading(true);
        presenter.deletePrestasi(item);
    }

    @OnClick(R.id.fab_add)
    void showAddPengalaman(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_insert_prestasi);

        TextView txtDesc = (TextView) dialog.findViewById(R.id.txt_desc);
        txtDesc.setText("Masukan pengalaman");
        final EditText inputPretasi = (EditText) dialog.findViewById(R.id.input_prestasi);

        inputPretasi.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inputPretasi.setHint("Pengalaman");
                return false;
            }

        });

        inputPretasi.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    inputPretasi.setHint("Pengalaman (contoh: Mengajar Matematika di SMA 1 Yogyakarta)");
                }
            }
        });

        Button btnPositif = (Button) dialog.findViewById(R.id.btn_positif);
        Button btnNegatif = (Button) dialog.findViewById(R.id.btn_negatif);

        btnPositif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = inputPretasi.getText().toString();
                if (TextUtils.isEmpty(name)){
                    inputPretasi.setError(errRequired);
                    inputPretasi.requestFocus();
                }else{
                    showLoading(true);
                    Pengalaman pengalaman = new Pengalaman(UUID.randomUUID().toString(), name);
                    presenter.updatePrestasi(pengalaman);
                }
                dialog.dismiss();
            }
        });

        btnNegatif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
