package net.margaritov.preference.colorpicker;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ColorPickerPreference
  extends DialogPreference
  implements ColorPickerDialog.OnColorChangedListener
{
  private boolean mAlphaSliderEnabled = false;
  int mAndroidColor = 0;
  int mDarkKatColor = this.mAndroidColor;
  int mDefaultValue = -16777216;
  private float mDensity = 0.0F;
  private EditText mEditText;
  private int mValue = -16777216;
  View mView;
  LinearLayout widgetFrameView;
  
  public ColorPickerPreference(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null);
  }
  
  public ColorPickerPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public ColorPickerPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  public static String convertToARGB(int paramInt)
  {
    Object localObject2 = Integer.toHexString(Color.alpha(paramInt));
    Object localObject3 = Integer.toHexString(Color.red(paramInt));
    Object localObject4 = Integer.toHexString(Color.green(paramInt));
    String str = Integer.toHexString(Color.blue(paramInt));
    Object localObject1 = localObject2;
    if (((String)localObject2).length() == 1) {
      localObject1 = "0" + (String)localObject2;
    }
    localObject2 = localObject3;
    if (((String)localObject3).length() == 1) {
      localObject2 = "0" + (String)localObject3;
    }
    localObject3 = localObject4;
    if (((String)localObject4).length() == 1) {
      localObject3 = "0" + (String)localObject4;
    }
    localObject4 = str;
    if (str.length() == 1) {
      localObject4 = "0" + str;
    }
    return "#" + (String)localObject1 + (String)localObject2 + (String)localObject3 + (String)localObject4;
  }
  
  public static int convertToColorInt(String paramString)
    throws NumberFormatException
  {
    String str = paramString;
    if (paramString.startsWith("#")) {
      str = paramString.replace("#", "");
    }
    int i = -1;
    int m = -1;
    int k = -1;
    int j = -1;
    if (str.length() == 8)
    {
      i = Integer.parseInt(str.substring(0, 2), 16);
      m = Integer.parseInt(str.substring(2, 4), 16);
      k = Integer.parseInt(str.substring(4, 6), 16);
      j = Integer.parseInt(str.substring(6, 8), 16);
    }
    for (;;)
    {
      return Color.argb(i, m, k, j);
      if (str.length() == 6)
      {
        i = 255;
        m = Integer.parseInt(str.substring(0, 2), 16);
        k = Integer.parseInt(str.substring(2, 4), 16);
        j = Integer.parseInt(str.substring(4, 6), 16);
      }
    }
  }
  
  private Bitmap getPreviewBitmap()
  {
    int i = (int)(this.mDensity * 31.0F);
    int m = getValue();
    Bitmap localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
    int n = localBitmap.getWidth();
    int i1 = localBitmap.getHeight();
    i = 0;
    while (i < n)
    {
      int j = i;
      if (j < i1)
      {
        if ((i <= 1) || (j <= 1) || (i >= n - 2) || (j >= i1 - 2)) {}
        for (int k = -7829368;; k = m)
        {
          localBitmap.setPixel(i, j, k);
          if (i != j) {
            localBitmap.setPixel(j, i, k);
          }
          j += 1;
          break;
        }
      }
      i += 1;
    }
    return localBitmap;
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet)
  {
    this.mDensity = getContext().getResources().getDisplayMetrics().density;
    String str;
    if (paramAttributeSet != null)
    {
      str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "defaultValue");
      if (!str.startsWith("#")) {
        break label110;
      }
    }
    for (;;)
    {
      try
      {
        this.mDefaultValue = convertToColorInt(str);
        this.mAlphaSliderEnabled = paramAttributeSet.getAttributeBooleanValue(null, "alphaSlider", true);
        this.mValue = this.mDefaultValue;
        return;
      }
      catch (NumberFormatException paramContext)
      {
        Log.e("ColorPickerPreference", "Wrong color: " + str);
        this.mDefaultValue = convertToColorInt("#FF000000");
        continue;
      }
      label110:
      int i = paramAttributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "defaultValue", 0);
      if (i != 0) {
        this.mDefaultValue = paramContext.getResources().getInteger(i);
      }
    }
  }
  
  private void setPreviewColor()
  {
    if (this.mView == null) {}
    ImageView localImageView;
    LinearLayout localLinearLayout;
    do
    {
      return;
      localImageView = new ImageView(getContext());
      localLinearLayout = (LinearLayout)this.mView.findViewById(16908312);
    } while (localLinearLayout == null);
    localLinearLayout.setVisibility(0);
    localLinearLayout.setPadding(localLinearLayout.getPaddingLeft(), localLinearLayout.getPaddingTop(), (int)(this.mDensity * 8.0F), localLinearLayout.getPaddingBottom());
    int i = localLinearLayout.getChildCount();
    if (i > 0) {
      localLinearLayout.removeViews(0, i);
    }
    localLinearLayout.addView(localImageView);
    localLinearLayout.setMinimumWidth(0);
    localImageView.setBackgroundDrawable(new AlphaPatternDrawable((int)(5.0F * this.mDensity)));
    localImageView.setImageBitmap(getPreviewBitmap());
  }
  
  protected Dialog createDialog()
  {
    ColorPickerDialog localColorPickerDialog = new ColorPickerDialog(getContext(), 2131558520, getValue(), this.mAndroidColor, this.mDarkKatColor);
    if (this.mAlphaSliderEnabled) {
      localColorPickerDialog.setAlphaSliderVisible(true);
    }
    localColorPickerDialog.setOnColorChangedListener(this);
    return localColorPickerDialog;
  }
  
  public int getValue()
  {
    try
    {
      if (isPersistent()) {
        this.mValue = getPersistedInt(this.mDefaultValue);
      }
      return this.mValue;
    }
    catch (ClassCastException localClassCastException)
    {
      for (;;)
      {
        this.mValue = this.mDefaultValue;
      }
    }
  }
  
  protected void onBindView(View paramView)
  {
    this.mView = paramView;
    super.onBindView(paramView);
    this.widgetFrameView = ((LinearLayout)paramView.findViewById(16908312));
    setPreviewColor();
  }
  
  public void onColorChanged(int paramInt)
  {
    if (isPersistent()) {
      persistInt(paramInt);
    }
    this.mValue = paramInt;
    setPreviewColor();
    try
    {
      getOnPreferenceChangeListener().onPreferenceChange(this, Integer.valueOf(paramInt));
      try
      {
        this.mEditText.setText(Integer.toString(paramInt, 16));
        return;
      }
      catch (NullPointerException localNullPointerException1) {}
    }
    catch (NullPointerException localNullPointerException2)
    {
      for (;;) {}
    }
  }
  
  protected void onSetInitialValue(boolean paramBoolean, Object paramObject)
  {
    if (paramBoolean) {}
    for (int i = getValue();; i = ((Integer)paramObject).intValue())
    {
      onColorChanged(i);
      return;
    }
  }
  
  public void setAlphaSliderEnabled(boolean paramBoolean)
  {
    this.mAlphaSliderEnabled = paramBoolean;
  }
  
  public void setDefaultColors(int paramInt1, int paramInt2)
  {
    this.mAndroidColor = paramInt1;
    this.mDarkKatColor = paramInt2;
  }
  
  public void setNewPreviewColor(int paramInt)
  {
    onColorChanged(paramInt);
  }
  
  protected void showDialog(Bundle paramBundle)
  {
    super.showDialog(paramBundle);
    paramBundle = (ColorPickerDialog)getDialog();
  }
}

