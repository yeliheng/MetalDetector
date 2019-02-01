package com.yeliheng.metaldetector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.preference.PreferenceManager;
import android.view.View;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * 创建者: Yeliheng
 * 时间: 2019/1/27 - 22:24
 */
public class Common {
    boolean vibrate;
    Context context = MyApplication.getContext();
    /**
    * 震动手机
    * */
    public void vibrate(){
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        context.getSystemService(VIBRATOR_SERVICE);//获得 一个震动的服务
        vibrator.vibrate(100);
    }

    /**
    * 判断震动的配置文件
    * */
    public boolean isVibrate() {
        vibrate = PreferenceManager.getDefaultSharedPreferences(
                context).getBoolean("vibrate", true);
        return vibrate;
    }

    /**
    * 写入配置文件
    * */

    public void writeSP(String key,Boolean val){
        SharedPreferences sp = context.getSharedPreferences("com.yeliheng.metaldetector_preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,val);
        editor.commit();
    }

    /**
    * 读取配置文件
    * */
    public Boolean readSP(String key){
        SharedPreferences sp = context.getSharedPreferences("com.yeliheng.metaldetector_preferences",MODE_PRIVATE);
        Boolean res = sp.getBoolean(key,true);
        return res;
    }

    /**
    * 首页SnackBar
    * */

    public void showSnackBar(View view){
        Snackbar.make(view,"建议先点击齿轮看看介绍",Snackbar.LENGTH_LONG).setAction("不再提示", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeSP("tip",false);
            }
        }).show();
    }

}
