package com.lesgood.guru.ui.update_information;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 6/7/17.
 */

public class UpdateInformationActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infromation);
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActivityComponent() {

    }

    @OnClick(R.id.btn_download)
    void unduh(){
        String url = "https://play.google.com/store/apps/details?id=com.lesgood.guru";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
