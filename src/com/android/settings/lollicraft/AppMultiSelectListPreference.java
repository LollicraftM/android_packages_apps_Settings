package com.android.settings.lollicraft;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AppMultiSelectListPreference
  extends DialogPreference
{
  private static final Comparator<MyApplicationInfo> sDisplayNameComparator = new Comparator()
  {
    private final Collator collator = Collator.getInstance();
    
    public final int compare(AppMultiSelectListPreference.MyApplicationInfo paramAnonymousMyApplicationInfo1, AppMultiSelectListPreference.MyApplicationInfo paramAnonymousMyApplicationInfo2)
    {
      return this.collator.compare(paramAnonymousMyApplicationInfo1.label, paramAnonymousMyApplicationInfo2.label);
    }
  };
  private CharSequence[] mEntries;
  private CharSequence[] mEntryValues;
  private Set<String> mNewValues = new HashSet();
  private final List<MyApplicationInfo> mPackageInfoList = new ArrayList();
  private boolean mPreferenceChanged;
  private Set<String> mValues = new HashSet();
  
  public AppMultiSelectListPreference(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public AppMultiSelectListPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    paramAttributeSet = paramContext.getPackageManager().getInstalledApplications(0);
    int i = 0;
    MyApplicationInfo localMyApplicationInfo;
    if (i < paramAttributeSet.size())
    {
      localObject = (ApplicationInfo)paramAttributeSet.get(i);
      if (paramContext.getPackageManager().getLaunchIntentForPackage(((ApplicationInfo)localObject).packageName) == null) {}
      for (;;)
      {
        i += 1;
        break;
        localMyApplicationInfo = new MyApplicationInfo();
        localMyApplicationInfo.info = ((ApplicationInfo)localObject);
        localMyApplicationInfo.label = localMyApplicationInfo.info.loadLabel(getContext().getPackageManager()).toString();
        this.mPackageInfoList.add(localMyApplicationInfo);
      }
    }
    paramContext = new ArrayList();
    paramAttributeSet = new ArrayList();
    Collections.sort(this.mPackageInfoList, sDisplayNameComparator);
    Object localObject = this.mPackageInfoList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      localMyApplicationInfo = (MyApplicationInfo)((Iterator)localObject).next();
      paramContext.add(localMyApplicationInfo.label);
      paramAttributeSet.add(localMyApplicationInfo.info.packageName);
    }
    new MyApplicationInfo();
    this.mEntries = new CharSequence[paramContext.size()];
    this.mEntryValues = new CharSequence[paramContext.size()];
    paramContext.toArray(this.mEntries);
    paramAttributeSet.toArray(this.mEntryValues);
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if ((paramBoolean) && (this.mPreferenceChanged))
    {
      Set localSet = this.mNewValues;
      if (callChangeListener(localSet)) {
        setValues(localSet);
      }
    }
    this.mPreferenceChanged = false;
  }
  
  protected Object onGetDefaultValue(TypedArray paramTypedArray, int paramInt)
  {
    paramTypedArray = paramTypedArray.getTextArray(paramInt);
    int i = paramTypedArray.length;
    HashSet localHashSet = new HashSet();
    paramInt = 0;
    while (paramInt < i)
    {
      localHashSet.add(paramTypedArray[paramInt].toString());
      paramInt += 1;
    }
    return localHashSet;
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    super.onPrepareDialogBuilder(paramBuilder);
    paramBuilder.setAdapter(new AppListAdapter(getContext()), null);
    this.mNewValues.clear();
    this.mNewValues.addAll(this.mValues);
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    if (paramBoolean) {}
    for (paramObject = getPersistedStringSet(this.mValues);; paramObject = (Set)paramObject)
    {
      setValues((Set)paramObject);
      return;
    }
  }
  
  public void setValues(Set<String> paramSet)
  {
    this.mValues.clear();
    this.mValues.addAll(paramSet);
    persistStringSet(paramSet);
  }
  
  protected void showDialog(Bundle paramBundle)
  {
    super.showDialog(paramBundle);
    paramBundle = ((AlertDialog)getDialog()).getListView();
    paramBundle.setItemsCanFocus(false);
    paramBundle.setChoiceMode(2);
    paramBundle.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        paramAnonymousAdapterView = (AppMultiSelectListPreference.AppViewHolder)paramAnonymousView.getTag();
        if (paramAnonymousAdapterView.checkBox.isChecked()) {}
        for (boolean bool = false;; bool = true)
        {
          paramAnonymousAdapterView.checkBox.setChecked(bool);
          if (!bool) {
            break;
          }
          paramAnonymousAdapterView = AppMultiSelectListPreference.this;
          AppMultiSelectListPreference.-set0(paramAnonymousAdapterView, AppMultiSelectListPreference.-get3(paramAnonymousAdapterView) | AppMultiSelectListPreference.-get1(AppMultiSelectListPreference.this).add(AppMultiSelectListPreference.-get0(AppMultiSelectListPreference.this)[paramAnonymousInt].toString()));
          return;
        }
        paramAnonymousAdapterView = AppMultiSelectListPreference.this;
        AppMultiSelectListPreference.-set0(paramAnonymousAdapterView, AppMultiSelectListPreference.-get3(paramAnonymousAdapterView) | AppMultiSelectListPreference.-get1(AppMultiSelectListPreference.this).remove(AppMultiSelectListPreference.-get0(AppMultiSelectListPreference.this)[paramAnonymousInt].toString()));
      }
    });
  }
  
  public class AppListAdapter
    extends ArrayAdapter<AppMultiSelectListPreference.MyApplicationInfo>
  {
    private final LayoutInflater mInflater;
    
    public AppListAdapter(Context paramContext)
    {
      super(0);
      this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
      addAll(AppMultiSelectListPreference.-get2(AppMultiSelectListPreference.this));
    }
    
    public AppMultiSelectListPreference.MyApplicationInfo getItem(int paramInt)
    {
      return (AppMultiSelectListPreference.MyApplicationInfo)AppMultiSelectListPreference.-get2(AppMultiSelectListPreference.this).get(paramInt);
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      paramView = AppMultiSelectListPreference.AppViewHolder.createOrRecycle(this.mInflater, paramView);
      paramViewGroup = paramView.rootView;
      AppMultiSelectListPreference.MyApplicationInfo localMyApplicationInfo = getItem(paramInt);
      paramView.appName.setText(localMyApplicationInfo.label);
      if (localMyApplicationInfo.info != null) {
        paramView.appIcon.setImageDrawable(localMyApplicationInfo.info.loadIcon(getContext().getPackageManager()));
      }
      for (;;)
      {
        paramView.checkBox.setChecked(AppMultiSelectListPreference.-get1(AppMultiSelectListPreference.this).contains(AppMultiSelectListPreference.-get0(AppMultiSelectListPreference.this)[paramInt].toString()));
        return paramViewGroup;
        paramView.appIcon.setImageDrawable(null);
      }
    }
  }
  
  public static class AppViewHolder
  {
    public ImageView appIcon;
    public TextView appName;
    public CheckBox checkBox;
    public View rootView;
    
    public static AppViewHolder createOrRecycle(LayoutInflater paramLayoutInflater, View paramView)
    {
      if (paramView == null)
      {
        paramLayoutInflater = paramLayoutInflater.inflate(R.layout.ad_excluded_app_item, null);
        paramView = new AppViewHolder();
        paramView.rootView = paramLayoutInflater;
        paramView.appName = ((TextView)paramLayoutInflater.findViewById(R.id.app_name));
        paramView.appIcon = ((ImageView)paramLayoutInflater.findViewById(R.id.app_icon));
        paramView.checkBox = ((CheckBox)paramLayoutInflater.findViewById(android.R.id.checkbox));
        paramLayoutInflater.setTag(paramView);
        return paramView;
      }
      return (AppViewHolder)paramView.getTag();
    }
  }
  
  class MyApplicationInfo
  {
    ApplicationInfo info;
    CharSequence label;
    
    MyApplicationInfo() {}
  }
}

