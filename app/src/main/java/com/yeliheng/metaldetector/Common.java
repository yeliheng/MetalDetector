package com.yeliheng.metaldetector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Vibrator;
import com.google.android.material.snackbar.Snackbar;
import androidx.preference.PreferenceManager;
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
     * 类型:Bool
    * */

    public void writeSharedPreferencesByBool(String key,Boolean val){
        SharedPreferences sp = context.getSharedPreferences("com.yeliheng.metaldetector_preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,val);
        editor.commit();
    }

    /**
     * 写入配置文件
     * 类型:String
     * */

    public void writeSharedPreferencesByString(String key,String val){
        SharedPreferences sp = context.getSharedPreferences("com.yeliheng.metaldetector_preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,val);
        editor.commit();
    }

    /**
     * 读取配置文件
     * 返回:Bool
    * */
    public Boolean readSharedPreferencesByBool(String key){
        SharedPreferences sp = context.getSharedPreferences("com.yeliheng.metaldetector_preferences",MODE_PRIVATE);
        Boolean res = sp.getBoolean(key,true);
        return res;
    }

    /**
    * 读取配置文件
    * 返回String
    * */

    public String readSharedPreferencesByString(String key){
        SharedPreferences sp = context.getSharedPreferences("com.yeliheng.metaldetector_preferences",MODE_PRIVATE);
        String res = sp.getString(key,"80");
        return res;
    }

    /**
    * 判断设备是否支持霍尔传感器
    * */

    public Boolean isMagneticSensorAvailable() {
        SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        return magneticSensor != null;
    }

    /**
    * 首页SnackBar
    * */

    public void showSnackBar(View view){
        Snackbar.make(view,"建议先点击齿轮看看介绍",Snackbar.LENGTH_LONG).setAction("不再提示", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeSharedPreferencesByBool("tip",false);
            }
        }).show();
    }

    /**
    * 对话框
    * */

    public void showDialog(Context context){
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("OOOOOOOps!")
                .setMessage("你的设备没有霍尔传感器！")
                .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    }
