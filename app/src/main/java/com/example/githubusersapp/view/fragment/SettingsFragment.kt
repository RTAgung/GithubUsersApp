package com.example.githubusersapp.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubusersapp.R
import com.example.githubusersapp.broadcast.AlarmReceiver

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var alarmKey: String
    private lateinit var activeAlarmPreference: SwitchPreference
    private lateinit var alarm: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        alarm = AlarmReceiver()
        alarmKey = resources.getString(R.string.set_key_alarm)
        activeAlarmPreference = findPreference<SwitchPreference>(alarmKey) as SwitchPreference
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == alarmKey) {
            if (activeAlarmPreference.isChecked)
                context?.let { alarm.setAlarm(it) }
            else
                context?.let { alarm.cancelAlarm(it) }
        }
    }
}