package com.lesgood.guru.ui.slide_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;

import com.lesgood.guru.R;

import agency.tango.materialintroscreen.SlideFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 9/23/17.
 */

public class KebijakanSlide extends SlideFragment {

    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.webView)
    WebView webView;

    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_custom_slide, container, false);
        ButterKnife.bind(this, view);

        String htmlString = "<p style=\"text-align: center;\"><strong>KEBIJAKAN PRIVASI</strong></p> <p style=\"text-align: center;\"> </p> <p>Dengan mengakses dan menggunakan seluruh atau sebagian layanan lesgood, baik website, aplikasi mobile maupun pelatihan, maka anda telah setuju dan mematui segala syarat, ketentuan, kebijakan serta peraturan lesgood baik yang telah dibuat saat ini maupun yang akan datang.</p> <ol> <li>Data anda</li> </ol> <p>Ketika anda melakukan pendaftaran akun lesgood, sebagai pengajar maupun sebagai murid, berarti anda telah menyerahkan data pribadi anda kepada kami. Data tersebut adalah seluruh data yang anda isi di formulir pendaftaran yaitu meliputi KTP, Lokasi, Nomor telepon, email, Profil pendidikan, dan lainnya termasuk alamat IP anda.</p> <ol start=\"2\"> <li>Perlindungan data anda</li> </ol> <p>Data yang anda berikan pada lesgood disimpan pada sebuah server dengan keamanan khusus. Kami juga melakukan enkripsi data anda untuk menghindari penggunaan data anda oleh pihak yang tidak berwenang.</p> <ol start=\"3\"> <li>Penggunaan data anda</li> </ol> <p>Kami menggunakan beberapa data pribadi anda untuk ditampilkan melalui website lesgood maupun aplikasi mobile lesgood dengan tujuan memberi informasi pada masyarakat tentang jasa les yang anda berikan. Kami juga akan menggunakan beberapa data anda untuk menjalin kerjasama dengan pihak ketiga dalam rangka penggunaan jasa anda dalam skala yang lebih luas.</p> <ol start=\"4\"> <li>Lesgood berhak unutk menghapus akun anda apabila kami menemukan kecurangan yang dilakukan oleh anda.</li> </ol> <p>Kebijakan privasi ini akan terus kami kembangkan. Oleh karena itu kami berharap pada konsumen Lesgood untuk selalu mengikuti perkembangan kebijakan ini.</p>";

        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.colorAccent;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimary;
    }

    @Override
    public boolean canMoveFurther() {
        return checkBox.isChecked();
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return getString(R.string.err_cant_move);
    }
}