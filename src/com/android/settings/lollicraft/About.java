package com.android.settings.lollicraft;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import com.android.settings.SettingsPreferenceFragment;
import java.util.ArrayList;
import java.util.Collections;

public class About
  extends SettingsPreferenceFragment
  implements Preference.OnPreferenceChangeListener
{
  Preference mDonateUrl;
  Preference mFacebookUrl;
  Preference mForumUrl;
  Preference mGoogleUrl;
  Preference mSiteUrl;
  Preference mSourceUrl;
  
  private void launchUrl(String paramString)
  {
    paramString = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    getActivity().startActivity(paramString);
  }
  
  protected int getMetricsCategory()
  {
    return 16;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131230846);
    getPreferenceScreen();
    getContentResolver();
    this.mSiteUrl = findPreference("lc_website");
    this.mForumUrl = findPreference("lc_group");
    this.mSourceUrl = findPreference("lc_techremix");
    this.mFacebookUrl = findPreference("lc_facebook");
    this.mGoogleUrl = findPreference("lc_google_plus");
    this.mDonateUrl = findPreference("lc_donate");
    paramBundle = (PreferenceGroup)findPreference("devs");
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (i < paramBundle.getPreferenceCount())
    {
      localArrayList.add(paramBundle.getPreference(i));
      i += 1;
    }
    paramBundle.removeAll();
    paramBundle.setOrderingAsAdded(false);
    Collections.shuffle(localArrayList);
    i = 0;
    while (i < localArrayList.size())
    {
      Preference localPreference = (Preference)localArrayList.get(i);
      localPreference.setOrder(i);
      paramBundle.addPreference(localPreference);
      i += 1;
    }
  }
  
  public boolean onPreferenceChange(Preference paramPreference, Object paramObject)
  {
    return false;
  }
  
  public boolean onPreferenceTreeClick(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    if (paramPreference == this.mSiteUrl) {
      launchUrl("http://www.lollicraft.blogspot.com/");
    }
    for (;;)
    {
      return true;
      if (paramPreference == this.mForumUrl)
      {
        launchUrl("http://fb.com/groups/lollicraft");
      }
      else if (paramPreference == this.mSourceUrl)
      {
        launchUrl("http://www.tech-remix.tk");
      }
      else if (paramPreference == this.mFacebookUrl)
      {
        launchUrl("https://www.facebook.com/lollicraft");
      }
      else if (paramPreference == this.mGoogleUrl)
      {
        launchUrl("https://plus.google.com/communities/116268767824572304001");
      }
      else if (paramPreference == this.mDonateUrl)
      {
        launchUrl("http://www.tech-remix.tk/p/contact-us.html");
      }
      else
      {
        if (!paramPreference.getKey().equals("share")) {
          break;
        }
        paramPreferenceScreen = new Intent();
        paramPreferenceScreen.setAction("android.intent.action.SEND");
        paramPreferenceScreen.setType("text/plain");
        paramPreferenceScreen.putExtra("android.intent.extra.TEXT", String.format(getActivity().getString(2131496714), new Object[0]));
        startActivity(Intent.createChooser(paramPreferenceScreen, getActivity().getString(2131496715)));
      }
    }
    return super.onPreferenceTreeClick(paramPreferenceScreen, paramPreference);
  }
}

