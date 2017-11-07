package com.lesgood.guru.data.helper;

import android.content.Context;
import android.widget.Toast;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by sim-x on 11/7/17.
 */

public class AppUtils {

  public static void  showToas(Context context,String msg){
    StyleableToast.makeText(context,msg, Toast.LENGTH_SHORT, R.style.MyToast).show();
  }
}
