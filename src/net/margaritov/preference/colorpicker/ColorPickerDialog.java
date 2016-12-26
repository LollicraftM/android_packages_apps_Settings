package net.margaritov.preference.colorpicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class ColorPickerDialog
  extends Dialog
  implements TextWatcher, View.OnClickListener, View.OnFocusChangeListener, PopupMenu.OnMenuItemClickListener, ColorPickerView.OnColorChangedListener
{
  private LinearLayout mActionBarEditHex;
  private LinearLayout mActionBarMain;
  private final int mAndroidColor;
  private boolean mAnimateColorTransition = true;
  private ImageButton mBackButton;
  private ColorPickerView mColorPicker;
  private View mColorPickerView;
  private Animator mColorTransitionAnimator;
  private final int mDarkKatColor;
  private View mDivider;
  private Animator mEditHexBarFadeInAnimator;
  private Animator mEditHexBarFadeOutAnimator;
  private ImageButton mEditHexButton;
  private EditText mHex;
  private ImageButton mHexBackButton;
  private boolean mHideEditHexBar = false;
  private final int mInitialColor;
  private boolean mIsPanelButtons = true;
  private OnColorChangedListener mListener;
  private ColorPickerPanelView mNewColor;
  private int mNewColorValue;
  private ColorPickerPanelView mOldColor;
  private ImageButton mPaletteButton;
  private ColorPickerPanelView[] mPanelViewButtons;
  private ImageButton mResetButton;
  private ContentResolver mResolver;
  private Resources mResources;
  private ImageButton mSetButton;
  
  public ColorPickerDialog(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramContext, paramInt1);
    this.mInitialColor = paramInt2;
    this.mAndroidColor = paramInt3;
    this.mDarkKatColor = paramInt4;
    this.mResolver = paramContext.getContentResolver();
    this.mResources = paramContext.getResources();
    setUp();
  }
  
  private int blendColors(int paramInt1, int paramInt2, float paramFloat)
  {
    float f1 = 1.0F - paramFloat;
    float f2 = Color.alpha(paramInt2);
    float f3 = Color.alpha(paramInt1);
    float f4 = Color.red(paramInt2);
    float f5 = Color.red(paramInt1);
    float f6 = Color.green(paramInt2);
    float f7 = Color.green(paramInt1);
    float f8 = Color.blue(paramInt2);
    float f9 = Color.blue(paramInt1);
    return Color.argb((int)(f2 * paramFloat + f3 * f1), (int)(f4 * paramFloat + f5 * f1), (int)(f6 * paramFloat + f7 * f1), (int)(f8 * paramFloat + f9 * f1));
  }
  
  private ValueAnimator createAlphaAnimator(int paramInt1, int paramInt2)
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofInt(new int[] { paramInt1, paramInt2 });
    localValueAnimator.setDuration(500L);
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue() / 100.0F;
        ColorPickerDialog.this.mActionBarMain.setAlpha(1.0F - f);
        ColorPickerDialog.this.mActionBarEditHex.setAlpha(f);
        ColorPickerDialog.this.mDivider.setAlpha(f);
      }
    });
    if (this.mHideEditHexBar)
    {
      localValueAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          ColorPickerDialog.this.mActionBarMain.setVisibility(0);
          ViewCompat.jumpDrawablesToCurrentState(ColorPickerDialog.this.mActionBarMain);
        }
      });
      localValueAnimator.addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          ColorPickerDialog.this.mActionBarEditHex.setVisibility(8);
          ColorPickerDialog.this.mDivider.setVisibility(8);
        }
      });
      return localValueAnimator;
    }
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        ColorPickerDialog.this.mActionBarEditHex.setVisibility(0);
        ViewCompat.jumpDrawablesToCurrentState(ColorPickerDialog.this.mActionBarEditHex);
        ColorPickerDialog.this.mDivider.setVisibility(0);
      }
    });
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        ColorPickerDialog.this.mActionBarMain.setVisibility(8);
      }
    });
    return localValueAnimator;
  }
  
  private ValueAnimator createColorTransitionAnimator(float paramFloat1, float paramFloat2)
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { paramFloat1, paramFloat2 });
    localValueAnimator.setDuration(500L);
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = paramAnonymousValueAnimator.getAnimatedFraction();
        if (ColorPickerDialog.this.mIsPanelButtons)
        {
          paramAnonymousValueAnimator = new int[8];
          i = 0;
          while (i < ColorPickerDialog.this.mPanelViewButtons.length)
          {
            paramAnonymousValueAnimator[i] = ColorPickerDialog.this.blendColors(ColorPickerDialog.this.mPanelViewButtons[i].getColor(), ColorPickerDialog.access$1100(ColorPickerDialog.this, ColorPickerDialog.access$1000(ColorPickerDialog.this), i), f);
            ColorPickerDialog.this.mPanelViewButtons[i].setColor(paramAnonymousValueAnimator[i]);
            i += 1;
          }
        }
        int i = ColorPickerDialog.this.blendColors(ColorPickerDialog.this.mNewColor.getColor(), ColorPickerDialog.this.mNewColorValue, f);
        ColorPickerDialog.this.mNewColor.setColor(i);
      }
    });
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        if (!ColorPickerDialog.this.mIsPanelButtons) {
          ColorPickerDialog.access$802(ColorPickerDialog.this, true);
        }
      }
    });
    return localValueAnimator;
  }
  
  private int getPalette()
  {
    return Settings.System.getInt(this.mResolver, "color_picker_palette", 0);
  }
  
  private int getPanelViewButtonColor(int paramInt1, int paramInt2)
  {
    TypedArray localTypedArray;
    if (paramInt1 == 0) {
      localTypedArray = this.mResources.obtainTypedArray(2131361975);
    }
    for (;;)
    {
      paramInt1 = this.mResources.getColor(localTypedArray.getResourceId(paramInt2, 0));
      localTypedArray.recycle();
      return paramInt1;
      if (paramInt1 == 1) {
        localTypedArray = this.mResources.obtainTypedArray(2131361976);
      } else {
        localTypedArray = this.mResources.obtainTypedArray(2131361977);
      }
    }
  }
  
  private void hideActionBarEditHex()
  {
    this.mEditHexBarFadeOutAnimator.start();
  }
  
  private void setPalette(int paramInt)
  {
    Settings.System.putInt(this.mResolver, "color_picker_palette", paramInt);
  }
  
  private void setUp()
  {
    getWindow().setFormat(1);
    getWindow().setLayout(-1, -1);
    requestWindowFeature(1);
    this.mColorPickerView = ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(2130968933, null);
    setContentView(this.mColorPickerView);
    this.mActionBarMain = ((LinearLayout)this.mColorPickerView.findViewById(2131952442));
    this.mActionBarEditHex = ((LinearLayout)this.mColorPickerView.findViewById(2131952445));
    this.mActionBarEditHex.setVisibility(8);
    this.mDivider = this.mColorPickerView.findViewById(2131951859);
    this.mDivider.setVisibility(8);
    this.mBackButton = ((ImageButton)this.mColorPickerView.findViewById(2131951898));
    this.mBackButton.setOnClickListener(this);
    this.mEditHexButton = ((ImageButton)this.mColorPickerView.findViewById(2131952443));
    this.mEditHexButton.setOnClickListener(this);
    this.mPaletteButton = ((ImageButton)this.mColorPickerView.findViewById(2131952444));
    this.mPaletteButton.setOnClickListener(this);
    this.mResetButton = ((ImageButton)this.mColorPickerView.findViewById(2131952432));
    if ((this.mAndroidColor != 0) && (this.mDarkKatColor != 0)) {
      this.mResetButton.setOnClickListener(this);
    }
    for (;;)
    {
      this.mHexBackButton = ((ImageButton)this.mColorPickerView.findViewById(2131952446));
      this.mHexBackButton.setOnClickListener(this);
      this.mHex = ((EditText)this.mColorPickerView.findViewById(2131952447));
      this.mHex.setText(ColorPickerPreference.convertToARGB(this.mInitialColor));
      this.mHex.setOnFocusChangeListener(this);
      this.mSetButton = ((ImageButton)this.mColorPickerView.findViewById(2131952448));
      this.mSetButton.setOnClickListener(this);
      this.mColorPicker = ((ColorPickerView)this.mColorPickerView.findViewById(2131951814));
      this.mColorPicker.setOnColorChangedListener(this);
      setUpPanelViewButtons();
      this.mOldColor = ((ColorPickerPanelView)this.mColorPickerView.findViewById(2131952449));
      this.mOldColor.setOnClickListener(this);
      this.mNewColor = ((ColorPickerPanelView)this.mColorPickerView.findViewById(2131952450));
      this.mNewColor.setOnClickListener(this);
      this.mNewColorValue = this.mInitialColor;
      this.mOldColor.setColor(this.mInitialColor);
      setupAnimators();
      this.mAnimateColorTransition = false;
      this.mColorPicker.setColor(this.mInitialColor, true);
      return;
      this.mResetButton.setVisibility(8);
    }
  }
  
  private void setUpPanelViewButtons()
  {
    TypedArray localTypedArray = this.mResources.obtainTypedArray(2131361974);
    this.mPanelViewButtons = new ColorPickerPanelView[8];
    int i = 0;
    while (i < this.mPanelViewButtons.length)
    {
      int j = localTypedArray.getResourceId(i, 0);
      this.mPanelViewButtons[i] = ((ColorPickerPanelView)this.mColorPickerView.findViewById(j));
      this.mPanelViewButtons[i].setOnClickListener(this);
      i += 1;
    }
    localTypedArray.recycle();
    updatePanelViewButtonsColor();
  }
  
  private void setupAnimators()
  {
    this.mColorPickerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public boolean onPreDraw()
      {
        ColorPickerDialog.this.mColorPickerView.getViewTreeObserver().removeOnPreDrawListener(this);
        ColorPickerDialog.access$102(ColorPickerDialog.this, false);
        ColorPickerDialog.access$202(ColorPickerDialog.this, ColorPickerDialog.this.createAlphaAnimator(0, 100));
        ColorPickerDialog.access$102(ColorPickerDialog.this, true);
        ColorPickerDialog.access$402(ColorPickerDialog.this, ColorPickerDialog.this.createAlphaAnimator(100, 0));
        return true;
      }
    });
    this.mColorTransitionAnimator = createColorTransitionAnimator(0.0F, 1.0F);
  }
  
  private void showActionBarEditHex()
  {
    this.mEditHexBarFadeInAnimator.start();
  }
  
  private void showPalettePopupMenu(View paramView)
  {
    paramView = new PopupMenu(getContext(), paramView);
    paramView.setOnMenuItemClickListener(this);
    paramView.inflate(2132017166);
    paramView.show();
  }
  
  private void showResetPopupMenu(View paramView)
  {
    paramView = new PopupMenu(getContext(), paramView);
    paramView.setOnMenuItemClickListener(this);
    paramView.inflate(2132017167);
    paramView.show();
  }
  
  private void updatePanelViewButtonsColor()
  {
    int i = 0;
    while (i < this.mPanelViewButtons.length)
    {
      this.mPanelViewButtons[i].setColor(getPanelViewButtonColor(getPalette(), i));
      i += 1;
    }
  }
  
  public void afterTextChanged(Editable paramEditable) {}
  
  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void onClick(View paramView)
  {
    if ((paramView.getId() == 2131951898) || (paramView.getId() == 2131952449) || (paramView.getId() == 2131952450))
    {
      if ((this.mListener != null) && (paramView.getId() == 2131952450)) {
        this.mListener.onColorChanged(this.mNewColor.getColor());
      }
      dismiss();
    }
    for (;;)
    {
      return;
      if (paramView.getId() == 2131952444)
      {
        showPalettePopupMenu(paramView);
        return;
      }
      if (paramView.getId() == 2131952443)
      {
        showActionBarEditHex();
        return;
      }
      if (paramView.getId() == 2131952432)
      {
        showResetPopupMenu(paramView);
        return;
      }
      if (paramView.getId() == 2131952446)
      {
        hideActionBarEditHex();
        return;
      }
      if (paramView.getId() == 2131952448) {
        paramView = this.mHex.getText().toString();
      }
      try
      {
        int i = ColorPickerPreference.convertToColorInt(paramView);
        this.mColorPicker.setColor(i, true);
        hideActionBarEditHex();
        return;
        i = 0;
        while (i < this.mPanelViewButtons.length)
        {
          int j = this.mPanelViewButtons[i].getId();
          if (paramView.getId() == j) {}
          try
          {
            this.mColorPicker.setColor(getPanelViewButtonColor(getPalette(), i), true);
            i += 1;
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
        }
      }
      catch (Exception paramView)
      {
        for (;;) {}
      }
    }
  }
  
  public void onColorChanged(int paramInt)
  {
    this.mNewColorValue = paramInt;
    if (!this.mAnimateColorTransition)
    {
      this.mAnimateColorTransition = true;
      this.mNewColor.setColor(this.mNewColorValue);
    }
    for (;;)
    {
      try
      {
        if (this.mHex != null) {
          this.mHex.setText(ColorPickerPreference.convertToARGB(paramInt));
        }
        return;
      }
      catch (Exception localException) {}
      this.mIsPanelButtons = false;
      this.mColorTransitionAnimator.start();
    }
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      this.mHex.removeTextChangedListener(this);
      ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
      return;
    }
    this.mHex.addTextChangedListener(this);
  }
  
  public boolean onMenuItemClick(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 2131952459)
    {
      setPalette(0);
      this.mColorTransitionAnimator.start();
      return true;
    }
    if (paramMenuItem.getItemId() == 2131952460)
    {
      setPalette(1);
      this.mColorTransitionAnimator.start();
      return true;
    }
    if (paramMenuItem.getItemId() == 2131952461)
    {
      setPalette(2);
      this.mColorTransitionAnimator.start();
      return true;
    }
    if (paramMenuItem.getItemId() == 2131952462)
    {
      this.mColorPicker.setColor(this.mAndroidColor, true);
      return true;
    }
    if (paramMenuItem.getItemId() == 2131952463)
    {
      this.mColorPicker.setColor(this.mDarkKatColor, true);
      return true;
    }
    return false;
  }
  
  public void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mOldColor.setColor(paramBundle.getInt("old_color"));
    this.mColorPicker.setColor(paramBundle.getInt("new_color"), true);
    updatePanelViewButtonsColor();
  }
  
  public Bundle onSaveInstanceState()
  {
    Bundle localBundle = super.onSaveInstanceState();
    localBundle.putInt("old_color", this.mOldColor.getColor());
    localBundle.putInt("new_color", this.mNewColor.getColor());
    return localBundle;
  }
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void setAlphaSliderVisible(boolean paramBoolean)
  {
    this.mColorPicker.setAlphaSliderVisible(paramBoolean);
  }
  
  public void setOnColorChangedListener(OnColorChangedListener paramOnColorChangedListener)
  {
    this.mListener = paramOnColorChangedListener;
  }
  
  public static abstract interface OnColorChangedListener
  {
    public abstract void onColorChanged(int paramInt);
  }
}

