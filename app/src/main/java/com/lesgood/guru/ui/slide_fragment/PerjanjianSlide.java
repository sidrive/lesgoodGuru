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

public class PerjanjianSlide extends SlideFragment {

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

        String htmlString = "<p><strong>PERJANJIAN PENGAJAR</strong></p> "
            + "<p>Lesgood adalah platfom yang menghubungkan pengajar Les dengan murid Les. Dengan mendaftar sebagai pengajar Lesgood Indonesia "
            + "maka pengajar telah setuju untuk mematuhi segala syarat, ketentuan, "
            + "kebijakan serta peraturan Lesgood Indonesia baik yang telah dibuat saat ini maupun yang akan datang yaitu:</p> "
            + "<ol> <li>Pengajar setuju untuk mengisi formulir pendaftaran pengajar dengan data yang sebenar-benarnya. "
            + "Lesgood akan melakukan verifikasi data pengajar melalui berbagai sumber dan Lesgood berhak untuk menolak formulir pengajar jika ditemukan ketidaksesuaian.</li> "
            + "<li>Apabila sesi belajar murid lesgood telah berahir dan murid ingin melanjutkan sesi, maka murid harus memperpanjang sesi melalui layanan Lesgood.</li>"
            + " <li>Durasi tiap pertemuan adalah minimal 100 menit atau sesuai kesepakatan dengan pihak murid.</li>"
            + " <li>Pengajar Lesgood merupakan mitra kerja Lesgood dan bukan merupakan tenaga kerja/ karyawan/ pegawai Lesgood Indonesia.</li> "
            + "<li>Apabila berhalangan mengajar, pengajar berhak menolak tawaran kontrak belajar dengan alasan apapun.</li> "
            + "<li>Pengajar bertanggungjawab penuh atas segala konten yang disi di layanan Lesgood.</li> "
            + "<li>Pengajar berhak menerima pembayaran hasil mengajar sesuai dengan tarif mengajar yang diisi pada tiap keahlian di saat transaksi.</li> "
            + "<li>Tarif mengajar yang diisi pengajar pada tiap keahlian secara otomatis akan ditambahkan margin sebesar 30% sebagai keuntungan Lesgood Indonesia.</li> "
            + "<li>Pengajar akan mendapat dana hasil mengajar sesuai jumlah pertemuan dengan murid.</li> "
            + "<li>Lesgood tidak bertanggungjawab atas segala risiko maupun kerugian yang dialami pengajar atas kelalaian pengajar maupun murid.</li> "
            + "<li>Pengajar maupun murid berhak untuk merubah jadwal yang telah disepakati atas kesepakatan kedua belah pihak.</li> "
            + "<li>Pengajar harus menyelesaikan kontrak belajar yang telah dibuat dengan murid.</li> </ol> "
            + "<ol start=\"13\"> <li>Pengajar setuju untuk mengikuti panduan mengajar Lesgood Indonesia.</li> "
            + "<li>Pengajar setuju atas kebijakan privasi layanan Lesgood Indonesia.</li> "
            + "<li>Pengajar setuju untuk menjaga nama baik Lesgood Indonesia.</li> "
            + "<li>Pengajar setuju menggunakan 5 pilar mengajar lesgood saat mengajar yaitu: <ul style=\"list-style-type: circle;\"> "
            + "<li>Tepat waktu</li> <li>Tanggung jawab</li> <li>Professional</li> <li>Persiapan mengajar</li> <li>Ramah</li> </ul> </li> </ol> "
            + "<p><strong>KETENTUAN PEMBAYARAN PENGAJAR LESGOOD</strong></p> "
            + "<ol> <li>Saldo pengajar akan bertambah dengan nominal sesuai tarif mengajar yang ditentukan pengajar.</li> "
            + "<li>Saldo pengajar akan bertambah saat pengajar selesai mengajar, yaitu setelah murid menekan tombol selesai mengajar.</li> "
            + "<li>Saldo pengajar dapat dicairkan setelah minimal 4 kali mengajar.</li> "
            + "<li>Kami menggunakan layanan pembayaran pihak ketiga, beban pencairan dana oleh pihak ketiga ditanggung oleh pengajar.</li> "
            + "<li>Biaya pencairan dana oleh pihak ketiga bervariasi mulai dari Rp.2.500 tiap kali pencairan dana.</li> </ol>";

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