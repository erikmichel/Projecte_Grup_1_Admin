package com.example.g1_admin.Controllers.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.g1_admin.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    String locale;

    SwitchPreference darkMode;
    ListPreference lang;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        PreferenceManager pm = getPreferenceManager();

        //Select theme of application
        darkMode = (SwitchPreference) pm.findPreference("pref_dark_mode");
        darkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isOn = (boolean) newValue;

                if (isOn){
                    //DarkMode it's ON
                    Toast toast = Toast.makeText(getContext(), "DarkMode ON", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //DarkMode it's OFF
                    Toast toast = Toast.makeText(getContext(), "DarkMode OFF", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return true;
            }
        });


        /*lang.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                int index = lang.findIndexOfValue(newValue.toString());

                if (index != -1) {
                    Toast.makeText(getContext(), lang.getEntries()[index], Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });*/


        /*lang = (ListPreference) pm.findPreference("pref_lang");
        if(lang.getEntries().equals("1")) {
            locale = "en_US";
        } else if (lang.getEntries().equals("2")){
            locale = "es_ES";
        } else {
            locale = "fr_FR";
        }*/
    }
}