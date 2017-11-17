package com.lesgood.guru.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseUser;
import com.lesgood.guru.R;
import com.lesgood.guru.base.BaseApplication;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import retrofit2.http.HEAD;

/**
 * Created by sim-x on 11/7/17.
 */

public class AppUtils {
  private FirebaseUser muser;
  public static void  showToas(Context context,String msg){
    StyleableToast.makeText(context,msg, Toast.LENGTH_SHORT, R.style.MyToast).show();
  }
  public static void setAvatar(Context context, String imgUrl, ImageView img){

    if(imgUrl!=null){
    if (!imgUrl.equalsIgnoreCase("NOT")){
      Glide.with(context).load(imgUrl).placeholder(R.color.colorSoft).dontAnimate().into(img);
      }
    }else {

      Glide.with(context).load(R.drawable.ic_account_circle_black).placeholder(R.color.colorSoft).dontAnimate().into(img);
    }
  }

}
