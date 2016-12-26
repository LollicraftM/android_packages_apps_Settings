package net.margaritov.preference.colorpicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;

public class ColorPickerPanelView
  extends View
{
  private AlphaPatternDrawable mAlphaPattern;
  private int mBorderColor = -9539986;
  private Paint mBorderPaint;
  private int mColor = -16777216;
  private Paint mColorPaint;
  private RectF mColorRect;
  private float mDensity = 1.0F;
  private RectF mDrawingRect;
  
  public ColorPickerPanelView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ColorPickerPanelView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ColorPickerPanelView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void init()
  {
    this.mBorderPaint = new Paint();
    this.mColorPaint = new Paint();
    this.mDensity = getContext().getResources().getDisplayMetrics().density;
  }
  
  private void setUpColorRect()
  {
    RectF localRectF = this.mDrawingRect;
    float f1 = localRectF.left;
    float f2 = localRectF.top;
    float f3 = localRectF.bottom;
    this.mColorRect = new RectF(f1 + 1.0F, f2 + 1.0F, localRectF.right - 1.0F, f3 - 1.0F);
    this.mAlphaPattern = new AlphaPatternDrawable((int)(5.0F * this.mDensity));
    this.mAlphaPattern.setBounds(Math.round(this.mColorRect.left), Math.round(this.mColorRect.top), Math.round(this.mColorRect.right), Math.round(this.mColorRect.bottom));
  }
  
  public int getColor()
  {
    return this.mColor;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    RectF localRectF = this.mColorRect;
    this.mBorderPaint.setColor(this.mBorderColor);
    paramCanvas.drawRect(this.mDrawingRect, this.mBorderPaint);
    if (this.mAlphaPattern != null) {
      this.mAlphaPattern.draw(paramCanvas);
    }
    this.mColorPaint.setColor(this.mColor);
    paramCanvas.drawRect(localRectF, this.mColorPaint);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mDrawingRect = new RectF();
    this.mDrawingRect.left = getPaddingLeft();
    this.mDrawingRect.right = (paramInt1 - getPaddingRight());
    this.mDrawingRect.top = getPaddingTop();
    this.mDrawingRect.bottom = (paramInt2 - getPaddingBottom());
    setUpColorRect();
  }
  
  public void setColor(int paramInt)
  {
    this.mColor = paramInt;
    invalidate();
  }
}

