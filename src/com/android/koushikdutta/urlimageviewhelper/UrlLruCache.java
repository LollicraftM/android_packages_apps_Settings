package com.koushikdutta.urlimageviewhelper;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class UrlLruCache
  extends LruCache<String, BitmapDrawable>
{
  public UrlLruCache(int paramInt)
  {
    super(paramInt);
  }
  
  protected int sizeOf(String paramString, BitmapDrawable paramBitmapDrawable)
  {
    if (paramBitmapDrawable != null)
    {
      paramString = paramBitmapDrawable.getBitmap();
      if (paramString != null) {
        return paramString.getRowBytes() * paramString.getHeight();
      }
    }
    return 0;
  }
}

