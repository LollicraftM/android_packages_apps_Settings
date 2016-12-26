package com.android.settings.lollicraft.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.lollicraft.AppMultiSelectListPreference;
import com.android.settings.lollicraft.SeekBarPreferenceCham;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AppCircleBar
  extends SettingsPreferenceFragment
  implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener
{
  private AppMultiSelectListPreference mIncludedAppCircleBar;
  private SeekBarPreferenceCham mTriggerBottomPref;
  private SeekBarPreferenceCham mTriggerTopPref;
  private SeekBarPreferenceCham mTriggerWidthPref;
  
  private Set<String> getIncludedApps()
  {
    String str = Settings.System.getString(getActivity().getContentResolver(), "whitelist_app_circle_bar");
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    return new HashSet(Arrays.asList(str.split("\\|")));
  }
  
  private void storeIncludedApps(Set<String> paramSet)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = "";
    Iterator localIterator = paramSet.iterator();
    for (paramSet = str; localIterator.hasNext(); paramSet = "|")
    {
      str = (String)localIterator.next();
      localStringBuilder.append(paramSet);
      localStringBuilder.append(str);
    }
    Settings.System.putString(getActivity().getContentResolver(), "whitelist_app_circle_bar", localStringBuilder.toString());
  }
  
  protected int getMetricsCategory()
  {
    return 239;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getActivity().getContentResolver();
    getResources();
    addPreferencesFromResource(R.xml.app_circle_sidebar);
    this.mIncludedAppCircleBar = ((AppMultiSelectListPreference)getPreferenceScreen().findPreference("app_circle_bar_included_apps"));
    paramBundle = getIncludedApps();
    if (paramBundle != null) {
      this.mIncludedAppCircleBar.setValues(paramBundle);
    }
    this.mIncludedAppCircleBar.setOnPreferenceChangeListener(this);
    this.mTriggerWidthPref = ((SeekBarPreferenceCham)findPreference("trigger_width"));
    this.mTriggerWidthPref.setValue(Settings.System.getInt(getContentResolver(), "app_circle_bar_trigger_width", 40));
    this.mTriggerWidthPref.setOnPreferenceChangeListener(this);
    this.mTriggerTopPref = ((SeekBarPreferenceCham)findPreference("trigger_top"));
    this.mTriggerTopPref.setValue(Settings.System.getInt(getContentResolver(), "app_circle_bar_trigger_top", 0));
    this.mTriggerTopPref.setOnPreferenceChangeListener(this);
    this.mTriggerBottomPref = ((SeekBarPreferenceCham)findPreference("trigger_bottom"));
    this.mTriggerBottomPref.setValue(Settings.System.getInt(getContentResolver(), "app_circle_bar_trigger_height", 100));
    this.mTriggerBottomPref.setOnPreferenceChangeListener(this);
  }
  
  public void onPause()
  {
    super.onPause();
    Settings.System.putInt(getContentResolver(), "app_circle_bar_show_trigger", 0);
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    getActivity().getContentResolver();
    paramPreference.getKey();
    if (paramPreference == this.mIncludedAppCircleBar) {
      storeIncludedApps((Set)paramObject);
    }
    do
    {
      return true;
      if (paramPreference == this.mTriggerWidthPref)
      {
        i = ((Integer)paramObject).intValue();
        Settings.System.putInt(getContentResolver(), "app_circle_bar_trigger_width", i);
        return true;
      }
      if (paramPreference == this.mTriggerTopPref)
      {
        i = ((Integer)paramObject).intValue();
        Settings.System.putInt(getContentResolver(), "app_circle_bar_trigger_top", i);
        return true;
      }
    } while (paramPreference != this.mTriggerBottomPref);
    int i = ((Integer)paramObject).intValue();
    Settings.System.putInt(getContentResolver(), "app_circle_bar_trigger_height", i);
    return true;
  }
  
  public boolean onPreferenceClick(Preference paramPreference)
  {
    return false;
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
  }
  
  public void onResume()
  {
    super.onResume();
    Settings.System.putInt(getContentResolver(), "app_circle_bar_show_trigger", 1);
  }
}

