package com.android.settings.lollicraft;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import com.android.settings.SettingsPreferenceFragment;

public class extras
  extends SettingsPreferenceFragment
  implements Preference.OnPreferenceChangeListener
{
  protected int getMetricsCategory()
  {
    return 16;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(R.xml.lollicraft_extras);
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    getActivity().getContentResolver();
    return false;
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
  }
  
  public void onResume()
  {
    super.onResume();
  }
}

