package net.margaritov.preference.colorpicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

public class ColorPickerView
  extends View
{
  private float ALPHA_PANEL_HEIGHT = 36.0F;
  private float HUE_PANEL_WIDTH = 36.0F;
  private float PALETTE_CIRCLE_TRACKER_RADIUS = 5.0F;
  private float PANEL_SPACING = 8.0F;
  private float RECTANGLE_TRACKER_OFFSET = 3.0F;
  private int mAlpha = 255;
  private Paint mAlphaPaint;
  private AlphaPatternDrawable mAlphaPattern;
  private RectF mAlphaRect;
  private Shader mAlphaShader;
  private String mAlphaSliderText = "";
  private Paint mAlphaTextPaint;
  private int mBorderColor = -9539986;
  private Paint mBorderPaint;
  private float mDensity = 1.0F;
  private float mDrawingOffset;
  private RectF mDrawingRect;
  private float mHue = 360.0F;
  private Paint mHuePaint;
  private RectF mHueRect;
  private Shader mHueShader;
  private Paint mHueTrackerPaint;
  private int mLastTouchedPanel = 0;
  private OnColorChangedListener mListener;
  private float mSat = 0.0F;
  private Shader mSatShader;
  private Paint mSatValPaint;
  private RectF mSatValRect;
  private Paint mSatValTrackerPaint;
  private boolean mShowAlphaPanel = false;
  private int mSliderTrackerColor = -14935012;
  private Point mStartTouchPoint = null;
  private float mVal = 0.0F;
  private Shader mValShader;
  
  public ColorPickerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ColorPickerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ColorPickerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private Point alphaToPoint(int paramInt)
  {
    RectF localRectF = this.mAlphaRect;
    float f = localRectF.width();
    Point localPoint = new Point();
    localPoint.x = ((int)(f - paramInt * f / 255.0F + localRectF.left));
    localPoint.y = ((int)localRectF.top);
    return localPoint;
  }
  
  private int[] buildHueColorArray()
  {
    int[] arrayOfInt = new int['ũ'];
    int i = 0;
    int j = arrayOfInt.length - 1;
    while (j >= 0)
    {
      arrayOfInt[i] = Color.HSVToColor(new float[] { j, 1.0F, 1.0F });
      j -= 1;
      i += 1;
    }
    return arrayOfInt;
  }
  
  private float calculateRequiredOffset()
  {
    return 1.5F * Math.max(Math.max(this.PALETTE_CIRCLE_TRACKER_RADIUS, this.RECTANGLE_TRACKER_OFFSET), 1.0F * this.mDensity);
  }
  
  private int chooseHeight(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == Integer.MIN_VALUE) || (paramInt1 == 1073741824)) {
      return paramInt2;
    }
    return getPrefferedHeight();
  }
  
  private int chooseWidth(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == Integer.MIN_VALUE) || (paramInt1 == 1073741824)) {
      return paramInt2;
    }
    return getPrefferedWidth();
  }
  
  private void drawAlphaPanel(Canvas paramCanvas)
  {
    if ((!this.mShowAlphaPanel) || (this.mAlphaRect == null) || (this.mAlphaPattern == null)) {
      return;
    }
    RectF localRectF1 = this.mAlphaRect;
    this.mBorderPaint.setColor(this.mBorderColor);
    paramCanvas.drawRect(localRectF1.left - 1.0F, localRectF1.top - 1.0F, 1.0F + localRectF1.right, 1.0F + localRectF1.bottom, this.mBorderPaint);
    this.mAlphaPattern.draw(paramCanvas);
    Object localObject = new float[3];
    localObject[0] = this.mHue;
    localObject[1] = this.mSat;
    localObject[2] = this.mVal;
    int i = Color.HSVToColor((float[])localObject);
    int j = Color.HSVToColor(0, (float[])localObject);
    this.mAlphaShader = new LinearGradient(localRectF1.left, localRectF1.top, localRectF1.right, localRectF1.top, i, j, Shader.TileMode.CLAMP);
    this.mAlphaPaint.setShader(this.mAlphaShader);
    paramCanvas.drawRect(localRectF1, this.mAlphaPaint);
    if ((this.mAlphaSliderText != null) && (this.mAlphaSliderText != "")) {
      paramCanvas.drawText(this.mAlphaSliderText, localRectF1.centerX(), localRectF1.centerY() + 4.0F * this.mDensity, this.mAlphaTextPaint);
    }
    float f = 4.0F * this.mDensity / 2.0F;
    localObject = alphaToPoint(this.mAlpha);
    RectF localRectF2 = new RectF();
    localRectF2.left = (((Point)localObject).x - f);
    localRectF2.right = (((Point)localObject).x + f);
    localRectF1.top -= this.RECTANGLE_TRACKER_OFFSET;
    localRectF1.bottom += this.RECTANGLE_TRACKER_OFFSET;
    paramCanvas.drawRoundRect(localRectF2, 2.0F, 2.0F, this.mHueTrackerPaint);
  }
  
  private void drawHuePanel(Canvas paramCanvas)
  {
    RectF localRectF1 = this.mHueRect;
    this.mBorderPaint.setColor(this.mBorderColor);
    paramCanvas.drawRect(localRectF1.left - 1.0F, localRectF1.top - 1.0F, localRectF1.right + 1.0F, 1.0F + localRectF1.bottom, this.mBorderPaint);
    if (this.mHueShader == null)
    {
      this.mHueShader = new LinearGradient(localRectF1.left, localRectF1.top, localRectF1.left, localRectF1.bottom, buildHueColorArray(), null, Shader.TileMode.CLAMP);
      this.mHuePaint.setShader(this.mHueShader);
    }
    paramCanvas.drawRect(localRectF1, this.mHuePaint);
    float f = 4.0F * this.mDensity / 2.0F;
    Point localPoint = hueToPoint(this.mHue);
    RectF localRectF2 = new RectF();
    localRectF1.left -= this.RECTANGLE_TRACKER_OFFSET;
    localRectF1.right += this.RECTANGLE_TRACKER_OFFSET;
    localRectF2.top = (localPoint.y - f);
    localRectF2.bottom = (localPoint.y + f);
    paramCanvas.drawRoundRect(localRectF2, 2.0F, 2.0F, this.mHueTrackerPaint);
  }
  
  private void drawSatValPanel(Canvas paramCanvas)
  {
    Object localObject = this.mSatValRect;
    this.mBorderPaint.setColor(this.mBorderColor);
    paramCanvas.drawRect(this.mDrawingRect.left, this.mDrawingRect.top, 1.0F + ((RectF)localObject).right, 1.0F + ((RectF)localObject).bottom, this.mBorderPaint);
    if (this.mValShader == null) {
      this.mValShader = new LinearGradient(((RectF)localObject).left, ((RectF)localObject).top, ((RectF)localObject).left, ((RectF)localObject).bottom, -1, -16777216, Shader.TileMode.CLAMP);
    }
    int i = Color.HSVToColor(new float[] { this.mHue, 1.0F, 1.0F });
    this.mSatShader = new LinearGradient(((RectF)localObject).left, ((RectF)localObject).top, ((RectF)localObject).right, ((RectF)localObject).top, -1, i, Shader.TileMode.CLAMP);
    ComposeShader localComposeShader = new ComposeShader(this.mValShader, this.mSatShader, PorterDuff.Mode.MULTIPLY);
    this.mSatValPaint.setShader(localComposeShader);
    paramCanvas.drawRect((RectF)localObject, this.mSatValPaint);
    localObject = satValToPoint(this.mSat, this.mVal);
    this.mSatValTrackerPaint.setColor(-16777216);
    paramCanvas.drawCircle(((Point)localObject).x, ((Point)localObject).y, this.PALETTE_CIRCLE_TRACKER_RADIUS - 1.0F * this.mDensity, this.mSatValTrackerPaint);
    this.mSatValTrackerPaint.setColor(-2236963);
    paramCanvas.drawCircle(((Point)localObject).x, ((Point)localObject).y, this.PALETTE_CIRCLE_TRACKER_RADIUS, this.mSatValTrackerPaint);
  }
  
  private int getPrefferedHeight()
  {
    int j = (int)(200.0F * this.mDensity);
    int i = j;
    if (this.mShowAlphaPanel) {
      i = (int)(j + (this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT));
    }
    return i;
  }
  
  private int getPrefferedWidth()
  {
    int j = getPrefferedHeight();
    int i = j;
    if (this.mShowAlphaPanel) {
      i = (int)(j - (this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT));
    }
    return (int)(i + this.HUE_PANEL_WIDTH + this.PANEL_SPACING);
  }
  
  private Point hueToPoint(float paramFloat)
  {
    RectF localRectF = this.mHueRect;
    float f = localRectF.height();
    Point localPoint = new Point();
    localPoint.y = ((int)(f - paramFloat * f / 360.0F + localRectF.top));
    localPoint.x = ((int)localRectF.left);
    return localPoint;
  }
  
  private void init()
  {
    setLayerType(1, null);
    this.mDensity = getContext().getResources().getDisplayMetrics().density;
    this.PALETTE_CIRCLE_TRACKER_RADIUS *= this.mDensity;
    this.RECTANGLE_TRACKER_OFFSET *= this.mDensity;
    this.HUE_PANEL_WIDTH *= this.mDensity;
    this.ALPHA_PANEL_HEIGHT *= this.mDensity;
    this.PANEL_SPACING *= this.mDensity;
    this.mDrawingOffset = calculateRequiredOffset();
    initPaintTools();
    setFocusable(true);
    setFocusableInTouchMode(true);
  }
  
  private void initPaintTools()
  {
    this.mSatValPaint = new Paint();
    this.mSatValTrackerPaint = new Paint();
    this.mHuePaint = new Paint();
    this.mHueTrackerPaint = new Paint();
    this.mAlphaPaint = new Paint();
    this.mAlphaTextPaint = new Paint();
    this.mBorderPaint = new Paint();
    this.mSatValTrackerPaint.setStyle(Paint.Style.STROKE);
    this.mSatValTrackerPaint.setStrokeWidth(this.mDensity * 2.0F);
    this.mSatValTrackerPaint.setAntiAlias(true);
    this.mHueTrackerPaint.setColor(this.mSliderTrackerColor);
    this.mHueTrackerPaint.setStyle(Paint.Style.STROKE);
    this.mHueTrackerPaint.setStrokeWidth(this.mDensity * 2.0F);
    this.mHueTrackerPaint.setAntiAlias(true);
    this.mAlphaTextPaint.setColor(-14935012);
    this.mAlphaTextPaint.setTextSize(14.0F * this.mDensity);
    this.mAlphaTextPaint.setAntiAlias(true);
    this.mAlphaTextPaint.setTextAlign(Paint.Align.CENTER);
    this.mAlphaTextPaint.setFakeBoldText(true);
  }
  
  private boolean moveTrackersIfNeeded(MotionEvent paramMotionEvent)
  {
    if (this.mStartTouchPoint == null) {}
    int i;
    int j;
    do
    {
      return false;
      i = this.mStartTouchPoint.x;
      j = this.mStartTouchPoint.y;
      if (this.mHueRect.contains(i, j))
      {
        this.mLastTouchedPanel = 1;
        this.mHue = pointToHue(paramMotionEvent.getY());
        return true;
      }
      if (this.mSatValRect.contains(i, j))
      {
        this.mLastTouchedPanel = 0;
        paramMotionEvent = pointToSatVal(paramMotionEvent.getX(), paramMotionEvent.getY());
        this.mSat = paramMotionEvent[0];
        this.mVal = paramMotionEvent[1];
        return true;
      }
    } while ((this.mAlphaRect == null) || (!this.mAlphaRect.contains(i, j)));
    this.mLastTouchedPanel = 2;
    this.mAlpha = pointToAlpha((int)paramMotionEvent.getX());
    return true;
  }
  
  private int pointToAlpha(int paramInt)
  {
    RectF localRectF = this.mAlphaRect;
    int i = (int)localRectF.width();
    if (paramInt < localRectF.left) {
      paramInt = 0;
    }
    for (;;)
    {
      return 255 - paramInt * 255 / i;
      if (paramInt > localRectF.right) {
        paramInt = i;
      } else {
        paramInt -= (int)localRectF.left;
      }
    }
  }
  
  private float pointToHue(float paramFloat)
  {
    RectF localRectF = this.mHueRect;
    float f = localRectF.height();
    if (paramFloat < localRectF.top) {
      paramFloat = 0.0F;
    }
    for (;;)
    {
      return 360.0F - paramFloat * 360.0F / f;
      if (paramFloat > localRectF.bottom) {
        paramFloat = f;
      } else {
        paramFloat -= localRectF.top;
      }
    }
  }
  
  private float[] pointToSatVal(float paramFloat1, float paramFloat2)
  {
    RectF localRectF = this.mSatValRect;
    float f1 = localRectF.width();
    float f2 = localRectF.height();
    if (paramFloat1 < localRectF.left)
    {
      paramFloat1 = 0.0F;
      if (paramFloat2 >= localRectF.top) {
        break label92;
      }
      paramFloat2 = 0.0F;
    }
    for (;;)
    {
      return new float[] { 1.0F / f1 * paramFloat1, 1.0F - 1.0F / f2 * paramFloat2 };
      if (paramFloat1 > localRectF.right)
      {
        paramFloat1 = f1;
        break;
      }
      paramFloat1 -= localRectF.left;
      break;
      label92:
      if (paramFloat2 > localRectF.bottom) {
        paramFloat2 = f2;
      } else {
        paramFloat2 -= localRectF.top;
      }
    }
  }
  
  private Point satValToPoint(float paramFloat1, float paramFloat2)
  {
    RectF localRectF = this.mSatValRect;
    float f1 = localRectF.height();
    float f2 = localRectF.width();
    Point localPoint = new Point();
    localPoint.x = ((int)(paramFloat1 * f2 + localRectF.left));
    localPoint.y = ((int)((1.0F - paramFloat2) * f1 + localRectF.top));
    return localPoint;
  }
  
  private void setUpAlphaRect()
  {
    if (!this.mShowAlphaPanel) {
      return;
    }
    RectF localRectF = this.mDrawingRect;
    float f1 = localRectF.left;
    float f2 = localRectF.bottom;
    float f3 = this.ALPHA_PANEL_HEIGHT;
    float f4 = localRectF.bottom;
    this.mAlphaRect = new RectF(f1 + 1.0F, f2 - f3 + 1.0F, localRectF.right - 1.0F, f4 - 1.0F);
    this.mAlphaPattern = new AlphaPatternDrawable((int)(5.0F * this.mDensity));
    this.mAlphaPattern.setBounds(Math.round(this.mAlphaRect.left), Math.round(this.mAlphaRect.top), Math.round(this.mAlphaRect.right), Math.round(this.mAlphaRect.bottom));
  }
  
  private void setUpHueRect()
  {
    RectF localRectF = this.mDrawingRect;
    float f2 = localRectF.right;
    float f3 = this.HUE_PANEL_WIDTH;
    float f4 = localRectF.top;
    float f5 = localRectF.bottom;
    if (this.mShowAlphaPanel) {}
    for (float f1 = this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT;; f1 = 0.0F)
    {
      this.mHueRect = new RectF(f2 - f3 + 1.0F, f4 + 1.0F, localRectF.right - 1.0F, f5 - 1.0F - f1);
      return;
    }
  }
  
  private void setUpSatValRect()
  {
    RectF localRectF = this.mDrawingRect;
    float f2 = localRectF.height() - 2.0F;
    float f1 = f2;
    if (this.mShowAlphaPanel) {
      f1 = f2 - (this.PANEL_SPACING + this.ALPHA_PANEL_HEIGHT);
    }
    f2 = localRectF.left + 1.0F;
    float f3 = localRectF.top + 1.0F;
    this.mSatValRect = new RectF(f2, f3, f2 + f1, f3 + f1);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if ((this.mDrawingRect.width() <= 0.0F) || (this.mDrawingRect.height() <= 0.0F)) {
      return;
    }
    drawSatValPanel(paramCanvas);
    drawHuePanel(paramCanvas);
    drawAlphaPanel(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getMode(paramInt1);
    int i = View.MeasureSpec.getMode(paramInt2);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    paramInt1 = chooseWidth(j, paramInt1);
    paramInt2 = chooseHeight(i, paramInt2);
    if (!this.mShowAlphaPanel)
    {
      i = (int)(paramInt1 - this.PANEL_SPACING - this.HUE_PANEL_WIDTH);
      if ((i > paramInt2) || ("landscape".equals(getTag())))
      {
        paramInt1 = paramInt2;
        paramInt2 = (int)(paramInt1 + this.PANEL_SPACING + this.HUE_PANEL_WIDTH);
      }
    }
    for (;;)
    {
      setMeasuredDimension(paramInt2, paramInt1);
      return;
      paramInt2 = paramInt1;
      paramInt1 = i;
      continue;
      i = (int)(paramInt2 - this.ALPHA_PANEL_HEIGHT + this.HUE_PANEL_WIDTH);
      if (i > paramInt1)
      {
        paramInt2 = paramInt1;
        paramInt1 = (int)(paramInt1 - this.HUE_PANEL_WIDTH + this.ALPHA_PANEL_HEIGHT);
      }
      else
      {
        paramInt1 = paramInt2;
        paramInt2 = i;
      }
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mDrawingRect = new RectF();
    this.mDrawingRect.left = (this.mDrawingOffset + getPaddingLeft());
    this.mDrawingRect.right = (paramInt1 - this.mDrawingOffset - getPaddingRight());
    this.mDrawingRect.top = (this.mDrawingOffset + getPaddingTop());
    this.mDrawingRect.bottom = (paramInt2 - this.mDrawingOffset - getPaddingBottom());
    setUpSatValRect();
    setUpHueRect();
    setUpAlphaRect();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = false;
    switch (paramMotionEvent.getAction())
    {
    }
    while (bool)
    {
      if (this.mListener != null) {
        this.mListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal }));
      }
      invalidate();
      return true;
      this.mStartTouchPoint = new Point((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      bool = moveTrackersIfNeeded(paramMotionEvent);
      continue;
      bool = moveTrackersIfNeeded(paramMotionEvent);
      continue;
      this.mStartTouchPoint = null;
      bool = moveTrackersIfNeeded(paramMotionEvent);
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean onTrackballEvent(MotionEvent paramMotionEvent)
  {
    float f2 = paramMotionEvent.getX();
    float f1 = paramMotionEvent.getY();
    int j = 0;
    int i = j;
    if (paramMotionEvent.getAction() == 2) {
      switch (this.mLastTouchedPanel)
      {
      default: 
        i = j;
      }
    }
    while (i != 0)
    {
      if (this.mListener != null) {
        this.mListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal }));
      }
      invalidate();
      return true;
      f2 = this.mSat + f2 / 50.0F;
      float f3 = this.mVal - f1 / 50.0F;
      if (f2 < 0.0F)
      {
        f1 = 0.0F;
        label149:
        if (f3 >= 0.0F) {
          break label187;
        }
        f2 = 0.0F;
      }
      for (;;)
      {
        this.mSat = f1;
        this.mVal = f2;
        i = 1;
        break;
        f1 = f2;
        if (f2 <= 1.0F) {
          break label149;
        }
        f1 = 1.0F;
        break label149;
        label187:
        f2 = f3;
        if (f3 > 1.0F) {
          f2 = 1.0F;
        }
      }
      f2 = this.mHue - f1 * 10.0F;
      if (f2 < 0.0F) {
        f1 = 0.0F;
      }
      for (;;)
      {
        this.mHue = f1;
        i = 1;
        break;
        f1 = f2;
        if (f2 > 360.0F) {
          f1 = 360.0F;
        }
      }
      if ((!this.mShowAlphaPanel) || (this.mAlphaRect == null))
      {
        i = 0;
      }
      else
      {
        j = (int)(this.mAlpha - f2 * 10.0F);
        if (j < 0) {
          i = 0;
        }
        for (;;)
        {
          this.mAlpha = i;
          i = 1;
          break;
          i = j;
          if (j > 255) {
            i = 255;
          }
        }
      }
    }
    return super.onTrackballEvent(paramMotionEvent);
  }
  
  public void setAlphaSliderVisible(boolean paramBoolean)
  {
    if (this.mShowAlphaPanel != paramBoolean)
    {
      this.mShowAlphaPanel = paramBoolean;
      this.mValShader = null;
      this.mSatShader = null;
      this.mHueShader = null;
      this.mAlphaShader = null;
      requestLayout();
    }
  }
  
  public void setColor(int paramInt, boolean paramBoolean)
  {
    int i = Color.alpha(paramInt);
    int j = Color.red(paramInt);
    int k = Color.blue(paramInt);
    paramInt = Color.green(paramInt);
    float[] arrayOfFloat = new float[3];
    Color.RGBToHSV(j, paramInt, k, arrayOfFloat);
    this.mAlpha = i;
    this.mHue = arrayOfFloat[0];
    this.mSat = arrayOfFloat[1];
    this.mVal = arrayOfFloat[2];
    if ((paramBoolean) && (this.mListener != null)) {
      this.mListener.onColorChanged(Color.HSVToColor(this.mAlpha, new float[] { this.mHue, this.mSat, this.mVal }));
    }
    invalidate();
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

