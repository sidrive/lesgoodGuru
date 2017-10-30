package com.lesgood.guru.ui.slide_fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
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

public class PanduanSlide extends SlideFragment {

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

        String htmlString = "<p style=\"text-align: center;\"><strong>PANDUAN MENGAJAR LESGOOD</strong></p> <p> </p> <ol> <li>Pengajar wajib menginstal aplikasi Lesgood mobile pada smasrtphone pengajar.</li> <li>Apablia ada murid memesan jasa les pengajar maka pengajar akan menerima notifikasi penawaran mengajar.</li> <li>Setelah murid melakukan pembayaran maka anda siap untuk mengajar.</li> <li>Isilah jadwal waktu luang anda untuk mengajar. Murid hanya dapat memesan Les anda pada jadwal waktu luang anda.</li> <li>Anda dapat mengaktifkan atau menonaktifkan akun pengajar anda. Apabila anda menonaktifkan akun pengajar anda maka anda tidak akan dapat menerima pesanan mengajar.</li> <li>Setelah mengajar mintalah murid untuk menekan tombol selesai belajar. Apabila murid memesan Les anda melalui aplikasi mobile murid maka mintalah murid menekan tombol selesai belajar pada aplikasi. Jika murid memesan melalui website maka mintalah murid menekan tombol selesai belajar pada website Lesgood.com.</li> <li>Apabila anda terpaksa tidak dapat melanjutkan kontrak belajar dengan murid maka anda dapat mencari pengajar pengganti anda.</li> <li>Setelah murid melakukan pembayaran silahkan menghubungi murid baik melalui telepon atau pesan text untuk memperkenalkan diri.</li> <li>Mintalah murid memberikan penilaian pada anda berupa bintang dan review. Penilaian ini akan sangat membantu reputasi mengajar anda.</li> <li>Sebelum pertemuan persiapkan materi ajar anda. Anda dapat mempelajari materi ajar anda dengan mendownload buku ajar di pustaka Lesgood.</li> <li>Saat mengajar bawalah fasilitas mengajar yang telah anda janjikan untuk diberikan pada murid.</li> </ol>";

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