package com.android.settings.lollicraft.pie;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;
import com.android.settings.SettingsPreferenceFragment;

public class Pie
  extends SettingsPreferenceFragment
  implements Preference.OnPreferenceChangeListener
{
  private CheckBoxPreference mPie;
  private ListPreference mPieAngle;
  private ListPreference mPieGap;
  private ListPreference mPieGravity;
  private ListPreference mPieMode;
  private ListPreference mPieSize;
  private ListPreference mPieTrigger;
  private ContentResolver mResolver;
  
  private void updatePieStatus(boolean paramBoolean)
  {
    ContentResolver localContentResolver = getActivity().getContentResolver();
    if (paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      Settings.System.putInt(localContentResolver, "pa_pie_controls", i);
      if ((Settings.System.getInt(getActivity().getContentResolver(), "expanded_desktop_style", 0) == 1) && (paramBoolean))
      {
        Settings.System.putInt(getActivity().getContentResolver(), "expanded_desktop_state", 0);
        Settings.System.putInt(getActivity().getContentResolver(), "expanded_desktop_style", 2);
      }
      return;
    }
  }
  
  protected int getMetricsCategory()
  {
    return 16;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131230854);
    paramBundle = getPreferenceScreen();
    this.mResolver = getActivity().getContentResolver();
    this.mPie = ((CheckBoxPreference)paramBundle.findPreference("pa_pie_controls"));
    int i = Settings.System.getInt(this.mResolver, "pa_pie_controls", 0);
    CheckBoxPreference localCheckBoxPreference = this.mPie;
    if (i != 0) {}
    for (boolean bool = true;; bool = false)
    {
      localCheckBoxPreference.setChecked(bool);
      this.mPieGravity = ((ListPreference)paramBundle.findPreference("pa_pie_gravity"));
      i = Settings.System.getInt(this.mResolver, "pa_pie_gravity", 3);
      this.mPieGravity.setValue(String.valueOf(i));
      this.mPieGravity.setSummary(this.mPieGravity.getEntry());
      this.mPieGravity.setOnPreferenceChangeListener(this);
      this.mPieMode = ((ListPreference)paramBundle.findPreference("pa_pie_mode"));
      i = Settings.System.getInt(this.mResolver, "pa_pie_mode", 1);
      this.mPieMode.setValue(String.valueOf(i));
      this.mPieMode.setSummary(this.mPieMode.getEntry());
      this.mPieMode.setOnPreferenceChangeListener(this);
      this.mPieGap = ((ListPreference)paramBundle.findPreference("pa_pie_gap"));
      i = Settings.System.getInt(this.mResolver, "pa_pie_gap", 2);
      this.mPieGap.setValue(String.valueOf(i));
      this.mPieGap.setSummary(this.mPieGap.getEntry());
      this.mPieGap.setOnPreferenceChangeListener(this);
      this.mPieAngle = ((ListPreference)paramBundle.findPreference("pa_pie_angle"));
      i = Settings.System.getInt(this.mResolver, "pa_pie_angle", 12);
      this.mPieAngle.setValue(String.valueOf(i));
      this.mPieAngle.setSummary(this.mPieAngle.getEntry());
      this.mPieAngle.setOnPreferenceChangeListener(this);
      this.mPieSize = ((ListPreference)paramBundle.findPreference("pa_pie_size"));
      float f = Settings.System.getFloat(this.mResolver, "pa_pie_size", 1.0F);
      this.mPieSize.setValue(String.valueOf(f));
      this.mPieSize.setSummary(this.mPieSize.getEntry());
      this.mPieSize.setOnPreferenceChangeListener(this);
      this.mPieTrigger = ((ListPreference)paramBundle.findPreference("pa_pie_trigger"));
      f = Settings.System.getFloat(this.mResolver, "pa_pie_trigger", 2.0F);
      this.mPieTrigger.setValue(String.valueOf(f));
      this.mPieTrigger.setSummary(this.mPieTrigger.getEntry());
      this.mPieTrigger.setOnPreferenceChangeListener(this);
      return;
    }
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    int i;
    int j;
    if (paramPreference == this.mPieMode)
    {
      i = Integer.valueOf((String)paramObject).intValue();
      j = this.mPieMode.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_mode", i);
      this.mPieMode.setSummary(this.mPieMode.getEntries()[j]);
      return true;
    }
    float f;
    if (paramPreference == this.mPieSize)
    {
      f = Float.valueOf((String)paramObject).floatValue();
      i = this.mPieSize.findIndexOfValue((String)paramObject);
      Settings.System.putFloat(getActivity().getContentResolver(), "pa_pie_size", f);
      this.mPieSize.setSummary(this.mPieSize.getEntries()[i]);
      return true;
    }
    if (paramPreference == this.mPieGravity)
    {
      i = Integer.valueOf((String)paramObject).intValue();
      j = this.mPieGravity.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_gravity", i);
      this.mPieGravity.setSummary(this.mPieGravity.getEntries()[j]);
      return true;
    }
    if (paramPreference == this.mPieTrigger)
    {
      f = Float.valueOf((String)paramObject).floatValue();
      i = this.mPieTrigger.findIndexOfValue((String)paramObject);
      Settings.System.putFloat(getActivity().getContentResolver(), "pa_pie_trigger", f);
      this.mPieTrigger.setSummary(this.mPieTrigger.getEntries()[i]);
      return true;
    }
    if (paramPreference == this.mPieMode)
    {
      i = Integer.valueOf((String)paramObject).intValue();
      j = this.mPieMode.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_mode", i);
      this.mPieMode.setSummary(this.mPieMode.getEntries()[j]);
      return true;
    }
    if (paramPreference == this.mPieAngle)
    {
      i = Integer.valueOf((String)paramObject).intValue();
      j = this.mPieAngle.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_angle", i);
      this.mPieAngle.setSummary(this.mPieAngle.getEntries()[j]);
      return true;
    }
    if (paramPreference == this.mPieGap)
    {
      i = Integer.valueOf((String)paramObject).intValue();
      j = this.mPieGap.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_gap", i);
      this.mPieGap.setSummary(this.mPieGap.getEntries()[j]);
      return true;
    }
    return false;
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    if (paramPreference == this.mPie) {
      updatePieStatus(this.mPie.isChecked());
    }
    return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
  }
}

