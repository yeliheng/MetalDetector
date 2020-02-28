package com.yeliheng.metaldetector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.white.progressview.CircleProgressView;
import java.math.BigDecimal;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 软件作者:Yeliheng
 * 官网:https://www.yeliheng.com
 * 源代码: https://github.com/yeliheng/MetalDetector
 * */
public class MainActivity extends AppCompatActivity implements SensorEventListener{
    final String TAG = "MetalDetector";
    private TextView mTextX;
    private TextView mTextY;
    private TextView mTextZ;
    private TextView mTotal;
    private SensorManager sensorManager;
    private CircleProgressView progressView;
    private TextView metalState;
    private LineChartView lineChart;
    private LineCharts lineCharts = new LineCharts();
    private Toolbar toolbar;
    double alarmLim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFunc();//初始化所有控件
        if(new Common().readSharedPreferencesByBool("tip")){
            new Common().showSnackBar(getWindow().getDecorView().getRootView());
        }

                if(!new Common().isMagneticSensorAvailable()){
                    new Common().showDialog(this);
                }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 实例化传感器管理
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //注册磁场传感器监听器
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Double rawTotal;//未处理的数据
        if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            //保持屏幕常亮
            if(new Common().readSharedPreferencesByBool("keep_wake")){
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }else{
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            //分别计算三轴磁感应强度
            float X_lateral = sensorEvent.values[0];
            float Y_lateral = sensorEvent.values[1];
            float Z_lateral = sensorEvent.values[2];
            //Log.d(TAG,X_lateral + "");
            //计算出总磁感应强度
            rawTotal = Math.sqrt(X_lateral * X_lateral + Y_lateral * Y_lateral + Z_lateral * Z_lateral);
            //初始化BigDecimal类
            BigDecimal total = new BigDecimal(rawTotal);
            double res = total.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            lineCharts.makeCharts(lineChart,(float) res);//实时绘图
            mTextX.setText(X_lateral + " μT");
            mTextY.setText(Y_lateral + " μT");
            mTextZ.setText(Z_lateral + " μT");
            mTotal.setText( res + " μT");
            String alarmLimStr = new Common().readSharedPreferencesByString("alarm_limit");
            //防止用户非法输入导致软件崩溃影响体验
            if(!alarmLimStr.isEmpty()){
                alarmLim = Double.valueOf(alarmLimStr);
            }else {
                alarmLim = 80;
            }
            if (res < alarmLim){
                metalState.setTextColor(Color.rgb(0,0,0));//设置文字颜色为黑色
                metalState.setText("未探测到金属");
                int progress = (int)((res / alarmLim )* 100);//计算进度
                progressView.setReachBarColor(Color.rgb(30,144,255));
                progressView.setProgress(progress);//进度条
            }else{
                metalState.setTextColor(Color.rgb(255,0,0));//红色
                metalState.setText("探测到金属!");
                progressView.setReachBarColor(Color.rgb(255,0,0));
                progressView.setProgress(100);//进度条满
                //震动
                if (new Common().isVibrate()){
                    new Common().vibrate();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private void initFunc(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextX = (TextView)findViewById(R.id.x);
        mTextY = (TextView)findViewById(R.id.y);
        mTextZ = (TextView)findViewById(R.id.z);
        mTotal = (TextView)findViewById(R.id.total);
        progressView = findViewById(R.id.totalMetalProgress);
        metalState = (TextView) findViewById(R.id.metalDetect);
        lineChart = (LineChartView) findViewById(R.id.chart);
        lineCharts.initView(lineChart);
    }
}
