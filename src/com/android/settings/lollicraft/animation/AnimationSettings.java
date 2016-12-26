package com.android.settings.lollicraft.animation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.SearchIndexableResource;
import android.provider.Settings.System;
import android.widget.Toast;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settings.search.Indexable.SearchIndexProvider;
import java.util.ArrayList;
import java.util.List;

public class AnimationSettings
  extends SettingsPreferenceFragment
  implements Preference.OnPreferenceChangeListener, Indexable
{
  public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new BaseSearchIndexProvider()
  {
    public List<String> getNonIndexableKeys(Context paramAnonymousContext)
    {
      return new ArrayList();
    }
    
    public List<SearchIndexableResource> getXmlResourcesToIndex(Context paramAnonymousContext, boolean paramAnonymousBoolean)
    {
      ArrayList localArrayList = new ArrayList();
      paramAnonymousContext = new SearchIndexableResource(paramAnonymousContext);
      paramAnonymousContext.xmlResId = R.xml.animation_settings;
      localArrayList.add(paramAnonymousContext);
      return localArrayList;
    }
  };
  private ListPreference mListViewAnimation;
  private ListPreference mListViewInterpolator;
  private ListPreference mPowerMenuAnimations;
  private ListPreference mToastAnimation;
  
  protected int getMetricsCategory()
  {
    return 39;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(R.xml.animation_settings);
    getActivity().getContentResolver();
    paramBundle = getPreferenceScreen();
    this.mToastAnimation = ((ListPreference)findPreference("toast_animation"));
    this.mToastAnimation.setSummary(this.mToastAnimation.getEntry());
    int i = Settings.System.getInt(getContentResolver(), "toast_animation", 1);
    this.mToastAnimation.setValueIndex(i);
    this.mToastAnimation.setSummary(this.mToastAnimation.getEntries()[i]);
    this.mToastAnimation.setOnPreferenceChangeListener(this);
    this.mListViewAnimation = ((ListPreference)paramBundle.findPreference("listview_animation"));
    i = Settings.System.getInt(getContentResolver(), "listview_animation", 0);
    this.mListViewAnimation.setValue(String.valueOf(i));
    this.mListViewAnimation.setSummary(this.mListViewAnimation.getEntry());
    this.mListViewAnimation.setOnPreferenceChangeListener(this);
    this.mListViewInterpolator = ((ListPreference)paramBundle.findPreference("listview_interpolator"));
    int j = Settings.System.getInt(getContentResolver(), "listview_interpolator", 0);
    this.mListViewInterpolator.setValue(String.valueOf(j));
    this.mListViewInterpolator.setSummary(this.mListViewInterpolator.getEntry());
    this.mListViewInterpolator.setOnPreferenceChangeListener(this);
    paramBundle = this.mListViewInterpolator;
    if (i > 0) {}
    for (boolean bool = true;; bool = false)
    {
      paramBundle.setEnabled(bool);
      this.mPowerMenuAnimations = ((ListPreference)findPreference("power_menu_animations"));
      this.mPowerMenuAnimations.setValue(String.valueOf(Settings.System.getInt(getContentResolver(), "power_menu_animations", 0)));
      this.mPowerMenuAnimations.setSummary(this.mPowerMenuAnimations.getEntry());
      this.mPowerMenuAnimations.setOnPreferenceChangeListener(this);
      return;
    }
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    getActivity().getContentResolver();
    int i;
    if (paramPreference == this.mToastAnimation)
    {
      i = this.mToastAnimation.findIndexOfValue((String)paramObject);
      Settings.System.putString(getContentResolver(), "toast_animation", (String)paramObject);
      this.mToastAnimation.setSummary(this.mToastAnimation.getEntries()[i]);
      Toast.makeText(this.mContext, "Toast Test", 0).show();
      return true;
    }
    int j;
    if (paramPreference == this.mListViewAnimation)
    {
      i = Integer.parseInt((String)paramObject);
      j = this.mListViewAnimation.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getContentResolver(), "listview_animation", i);
      this.mListViewAnimation.setSummary(this.mListViewAnimation.getEntries()[j]);
      paramPreference = this.mListViewInterpolator;
      if (i > 0) {}
      for (boolean bool = true;; bool = false)
      {
        paramPreference.setEnabled(bool);
        return true;
      }
    }
    if (paramPreference == this.mListViewInterpolator)
    {
      i = Integer.parseInt((String)paramObject);
      j = this.mListViewInterpolator.findIndexOfValue((String)paramObject);
      Settings.System.putInt(getContentResolver(), "listview_interpolator", i);
      this.mListViewInterpolator.setSummary(this.mListViewInterpolator.getEntries()[j]);
      return true;
    }
    if (paramPreference == this.mPowerMenuAnimations)
    {
      Settings.System.putInt(getContentResolver(), "power_menu_animations", Integer.valueOf((String)paramObject).intValue());
      this.mPowerMenuAnimations.setValue(String.valueOf(paramObject));
      this.mPowerMenuAnimations.setSummary(this.mPowerMenuAnimations.getEntry());
      return true;
    }
    return false;
  }
  
  public void onResume()
  {
    super.onResume();
  }
}

