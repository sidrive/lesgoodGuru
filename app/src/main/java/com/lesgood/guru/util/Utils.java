package com.lesgood.guru.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public static String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yyyy");
        return format.format(date);
    }
    public static String longToString(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
        return format.format(date);
    }
    public static String dayFormated(String day){
        String dayFormated;
        if (day.equals("Sun")){
            dayFormated = "Minggu";
        }else if (day.equals("Mon")){
            dayFormated = "Senen";
        }
        else if (day.equals("Tue")){
            dayFormated = "Selasa";
        }
        else if (day.equals("Wed")){
            dayFormated = "Rabu";
        }
        else if (day.equals("Thu")){
            dayFormated = "Kamis";
        }
        else if (day.equals("Fri")){
            dayFormated = "Jum'at";
        }
        else {
            dayFormated = "Sabtu";
        }
        return dayFormated;
    }
}
