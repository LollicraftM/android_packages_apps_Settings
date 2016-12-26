package net.margaritov.preference.colorpicker;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class AlphaPatternDrawable
  extends Drawable
{
  private Bitmap mBitmap;
  private Paint mPaint = new Paint();
  private Paint mPaintGray = new Paint();
  private Paint mPaintWhite = new Paint();
  private int mRectangleSize = 10;
  private int numRectanglesHorizontal;
  private int numRectanglesVertical;
  
  public AlphaPatternDrawable(int paramInt)
  {
    this.mRectangleSize = paramInt;
    this.mPaintWhite.setColor(-1);
    this.mPaintGray.setColor(-3421237);
  }
  
  private void generatePatternBitmap()
  {
    if ((getBounds().width() <= 0) || (getBounds().height() <= 0)) {
      return;
    }
    this.mBitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(this.mBitmap);
    Rect localRect = new Rect();
    int i = 1;
    int k = 0;
    label71:
    if (k <= this.numRectanglesVertical)
    {
      int j = i;
      int m = 0;
      if (m <= this.numRectanglesHorizontal)
      {
        localRect.top = (this.mRectangleSize * k);
        localRect.left = (this.mRectangleSize * m);
        localRect.bottom = (localRect.top + this.mRectangleSize);
        localRect.right = (localRect.left + this.mRectangleSize);
        Paint localPaint;
        if (j != 0)
        {
          localPaint = this.mPaintWhite;
          label154:
          localCanvas.drawRect(localRect, localPaint);
          if (j != 0) {
            break label185;
          }
        }
        label185:
        for (j = 1;; j = 0)
        {
          m += 1;
          break;
          localPaint = this.mPaintGray;
          break label154;
        }
      }
      if (i != 0) {
        break label208;
      }
    }
    label208:
    for (i = 1;; i = 0)
    {
      k += 1;
      break label71;
      break;
    }
  }
  
  public void draw(Canvas paramCanvas)
  {
    paramCanvas.drawBitmap(this.mBitmap, null, getBounds(), this.mPaint);
  }
  
  public int getOpacity()
  {
    return 0;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    int i = paramRect.height();
    this.numRectanglesHorizontal = ((int)Math.ceil(paramRect.width() / this.mRectangleSize));
    this.numRectanglesVertical = ((int)Math.ceil(i / this.mRectangleSize));
    generatePatternBitmap();
  }
  
  public void setAlpha(int paramInt)
  {
    throw new UnsupportedOperationException("Alpha is not supported by this drawwable.");
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    throw new UnsupportedOperationException("ColorFilter is not supported by this drawwable.");
  }
}

