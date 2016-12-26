package com.android.settings.lollicraft.pie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings.System;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.settings.SettingsPreferenceFragment;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

public class PieColor
  extends SettingsPreferenceFragment
  implements Preference.OnPreferenceChangeListener
{
  ColorPickerPreference mBtnColor;
  ColorPickerPreference mChevronLeft;
  ColorPickerPreference mChevronRight;
  CheckBoxPreference mEnableColor;
  ColorPickerPreference mJuice;
  ColorPickerPreference mOutlines;
  ColorPickerPreference mPieBg;
  MenuItem mReset;
  ColorPickerPreference mSelect;
  ColorPickerPreference mStatus;
  ColorPickerPreference mStatusClock;
  
  private void resetToDefault()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(getActivity());
    localBuilder.setTitle(2131496678);
    localBuilder.setMessage(2131493892);
    localBuilder.setPositiveButton(2131493338, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        PieColor.this.resetValues();
      }
    });
    localBuilder.setNegativeButton(2131494119, null);
    localBuilder.create().show();
  }
  
  private void resetValues()
  {
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_background", -1439485133);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_select", -1426063361);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_outlines", -1);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_status_clock", -1);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_status", -1);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_chevron_left", -542529111);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_chevron_right", -538713117);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_button_color", -1291845633);
    Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_juice", -5921371);
    this.mPieBg.setNewPreviewColor(-1439485133);
    this.mJuice.setNewPreviewColor(-5921371);
    this.mSelect.setNewPreviewColor(-1426063361);
    this.mOutlines.setNewPreviewColor(-1);
    this.mStatusClock.setNewPreviewColor(-1);
    this.mStatus.setNewPreviewColor(-1);
    this.mChevronLeft.setNewPreviewColor(-542529111);
    this.mChevronRight.setNewPreviewColor(-538713117);
    this.mBtnColor.setNewPreviewColor(-1291845633);
  }
  
  protected int getMetricsCategory()
  {
    return 16;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131230853);
    this.mEnableColor = ((CheckBoxPreference)findPreference("pa_pie_enable_color"));
    paramBundle = this.mEnableColor;
    if (Settings.System.getInt(getContentResolver(), "pa_pie_enable_color", 0) == 1) {}
    for (boolean bool = true;; bool = false)
    {
      paramBundle.setChecked(bool);
      this.mPieBg = ((ColorPickerPreference)findPreference("pa_pie_background"));
      this.mPieBg.setOnPreferenceChangeListener(this);
      this.mJuice = ((ColorPickerPreference)findPreference("pa_pie_juice"));
      this.mJuice.setOnPreferenceChangeListener(this);
      this.mSelect = ((ColorPickerPreference)findPreference("pa_pie_select"));
      this.mSelect.setOnPreferenceChangeListener(this);
      this.mOutlines = ((ColorPickerPreference)findPreference("pa_pie_outlines"));
      this.mOutlines.setOnPreferenceChangeListener(this);
      this.mStatusClock = ((ColorPickerPreference)findPreference("pa_pie_status_clock"));
      this.mStatusClock.setOnPreferenceChangeListener(this);
      this.mStatus = ((ColorPickerPreference)findPreference("pa_pie_status"));
      this.mStatus.setOnPreferenceChangeListener(this);
      this.mChevronLeft = ((ColorPickerPreference)findPreference("pa_pie_chevron_left"));
      this.mChevronLeft.setOnPreferenceChangeListener(this);
      this.mChevronRight = ((ColorPickerPreference)findPreference("pa_pie_chevron_right"));
      this.mChevronRight.setOnPreferenceChangeListener(this);
      this.mBtnColor = ((ColorPickerPreference)findPreference("pa_pie_button_color"));
      this.mBtnColor.setOnPreferenceChangeListener(this);
      setHasOptionsMenu(true);
      return;
    }
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    paramMenu.add(0, 1, 0, 2131496678).setIcon(2130837674).setShowAsAction(2);
    this.mReset = paramMenu.findItem(1);
    this.mReset.setVisible(this.mEnableColor.isChecked());
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default: 
      return super.onContextItemSelected(paramMenuItem);
    }
    resetToDefault();
    return true;
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    int i;
    if (paramPreference == this.mPieBg)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_background", i);
      return true;
    }
    if (paramPreference == this.mSelect)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_select", i);
      return true;
    }
    if (paramPreference == this.mOutlines)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_outlines", i);
      return true;
    }
    if (paramPreference == this.mStatusClock)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_status_clock", i);
      return true;
    }
    if (paramPreference == this.mStatus)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_status", i);
      return true;
    }
    if (paramPreference == this.mChevronLeft)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_chevron_left", i);
      return true;
    }
    if (paramPreference == this.mChevronRight)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_chevron_right", i);
      return true;
    }
    if (paramPreference == this.mBtnColor)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_button_color", i);
      return true;
    }
    if (paramPreference == this.mJuice)
    {
      i = ColorPickerPreference.convertToColorInt(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(paramObject)).intValue()));
      Settings.System.putInt(getActivity().getContentResolver(), "pa_pie_juice", i);
      return true;
    }
    return false;
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    ContentResolver localContentResolver;
    if (paramPreference == this.mEnableColor)
    {
      localContentResolver = getActivity().getContentResolver();
      if (!this.mEnableColor.isChecked()) {
        break label62;
      }
    }
    label62:
    for (int i = 1;; i = 0)
    {
      Settings.System.putInt(localContentResolver, "pa_pie_enable_color", i);
      this.mReset.setVisible(this.mEnableColor.isChecked());
      return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
    }
  }
}

