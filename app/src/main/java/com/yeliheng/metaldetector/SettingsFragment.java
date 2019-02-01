package com.yeliheng.metaldetector;

import android.os.Bundle;

/**
 * 创建者: Yeliheng
 * 时间: 2019/1/27 - 21:22
 */
import android.support.v7.preference.PreferenceFragmentCompat;
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        //加载资源文件
        addPreferencesFromResource(R.xml.pref_settings);
    }


}