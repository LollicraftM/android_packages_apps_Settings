package com.android.settings.lollicraft.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.preference.SwitchPreference;
import android.provider.Settings.System;
import android.util.AttributeSet;

public class SystemSettingSwitchPreference
  extends SwitchPreference
{
  public SystemSettingSwitchPreference(Context paramContext)
  {
    super(paramContext, null);
  }
  
  public SystemSettingSwitchPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public SystemSettingSwitchPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected boolean getPersistedBoolean(boolean paramBoolean)
  {
    if (!shouldPersist()) {
      return paramBoolean;
    }
    ContentResolver localContentResolver = getContext().getContentResolver();
    String str = getKey();
    if (paramBoolean) {}
    for (int i = 1; Settings.System.getInt(localContentResolver, str, i) != 0; i = 0) {
      return true;
    }
    return false;
  }
  
  protected boolean isPersisted()
  {
    return Settings.System.getString(getContext().getContentResolver(), getKey()) != null;
  }
  
  protected boolean persistBoolean(boolean paramBoolean)
  {
    int i = 0;
    if (shouldPersist())
    {
      if (paramBoolean) {}
      for (boolean bool = false; paramBoolean == getPersistedBoolean(bool); bool = true) {
        return true;
      }
      ContentResolver localContentResolver = getContext().getContentResolver();
      String str = getKey();
      if (paramBoolean) {
        i = 1;
      }
      Settings.System.putInt(localContentResolver, str, i);
      return true;
    }
    return false;
  }
}

