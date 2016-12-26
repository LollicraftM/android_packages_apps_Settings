package com.koushikdutta.urlimageviewhelper;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public abstract interface UrlImageViewCallback
{
  public abstract void onLoaded(ImageView paramImageView, Drawable paramDrawable, String paramString, boolean paramBoolean);
}

