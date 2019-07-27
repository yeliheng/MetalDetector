package com.yeliheng.metaldetector;

import android.content.res.Configuration;
import android.os.Bundle;

/**
 * 创建者: Yeliheng
 * 时间: 2019/1/27 - 21:22
 */
import com.takisoft.fix.support.v7.preference.EditTextPreference;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferencesFix(Bundle bundle, String s) {
        //加载资源文件
        addPreferencesFromResource(R.xml.pref_settings);
    }

}