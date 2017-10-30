package com.lesgood.guru.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Agus on 5/10/17.
 */

public class Utils {
    public static String getRupiah(int amount){
        String angka = Integer.toString(amount);
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka));
        return  "Rp."+rupiah;
    }
}
