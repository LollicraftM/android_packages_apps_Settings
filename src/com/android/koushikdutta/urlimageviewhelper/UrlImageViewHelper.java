package com.koushikdutta.urlimageviewhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Looper;
import android.provider.ContactsContract.Contacts;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import junit.framework.Assert;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public final class UrlImageViewHelper
{
  private static HashSet<BitmapDrawable> mAllCache;
  private static UrlLruCache mDeadCache;
  private static UrlDownloader mDefaultDownloader;
  private static UrlDownloader mDownloader;
  private static boolean mHasCleaned = false;
  private static UrlImageCache mLiveCache;
  static DisplayMetrics mMetrics;
  private static Hashtable<String, ArrayList<ImageView>> mPendingDownloads = new Hashtable();
  private static Hashtable<ImageView, String> mPendingViews;
  private static RequestPropertiesCallback mRequestPropertiesCallback;
  static Resources mResources;
  
  static
  {
    mDefaultDownloader = new UrlDownloader()
    {
      public void download(final Context paramAnonymousContext, final String paramAnonymousString1, final String paramAnonymousString2, final Runnable paramAnonymousRunnable1, final Runnable paramAnonymousRunnable2)
      {
        UrlImageViewHelper.-wrap1(new AsyncTask()
        {
          protected Void doInBackground(Void... paramAnonymous2VarArgs)
          {
            for (;;)
            {
              Object localObject1;
              try
              {
                if (paramAnonymousString1.startsWith(ContactsContract.Contacts.CONTENT_URI.toString()))
                {
                  paramAnonymous2VarArgs = ContactsContract.Contacts.openContactPhotoInputStream(paramAnonymousContext.getContentResolver(), Uri.parse(paramAnonymousString1));
                  if (paramAnonymous2VarArgs != null)
                  {
                    localObject1 = new FileOutputStream(paramAnonymousString2);
                    UrlImageViewHelper.copyStream(paramAnonymous2VarArgs, (OutputStream)localObject1);
                    ((FileOutputStream)localObject1).close();
                    paramAnonymous2VarArgs.close();
                  }
                  paramAnonymousRunnable1.run();
                  return null;
                }
                paramAnonymous2VarArgs = AndroidHttpClient.newInstance(paramAnonymousContext.getPackageName());
                localObject1 = new HttpGet(paramAnonymousString1);
                BasicHttpParams localBasicHttpParams = new BasicHttpParams();
                HttpClientParams.setRedirecting(localBasicHttpParams, true);
                if (UrlImageViewHelper.-get5() != null)
                {
                  Object localObject2 = UrlImageViewHelper.-get5().getHeadersForRequest(paramAnonymousContext, paramAnonymousString1);
                  if (localObject2 != null)
                  {
                    localObject2 = ((Iterable)localObject2).iterator();
                    if (((Iterator)localObject2).hasNext())
                    {
                      NameValuePair localNameValuePair = (NameValuePair)((Iterator)localObject2).next();
                      localBasicHttpParams.setParameter(localNameValuePair.getName(), localNameValuePair.getValue());
                      continue;
                    }
                  }
                }
                ((HttpGet)localObject1).setParams(localBasicHttpParams);
              }
              catch (Throwable paramAnonymous2VarArgs)
              {
                paramAnonymous2VarArgs.printStackTrace();
                return null;
              }
              paramAnonymous2VarArgs = paramAnonymous2VarArgs.execute((HttpUriRequest)localObject1);
              if (paramAnonymous2VarArgs.getStatusLine().getStatusCode() != 200) {
                return null;
              }
              paramAnonymous2VarArgs = paramAnonymous2VarArgs.getEntity().getContent();
            }
          }
          
          protected void onPostExecute(Void paramAnonymous2Void)
          {
            paramAnonymousRunnable2.run();
          }
        });
      }
    };
    mLiveCache = UrlImageCache.getInstance();
    mAllCache = new HashSet();
    mDownloader = mDefaultDownloader;
    mPendingViews = new Hashtable();
  }
  
  private static void cleanup(Context paramContext)
  {
    if (mHasCleaned) {
      return;
    }
    mHasCleaned = true;
    for (;;)
    {
      int i;
      try
      {
        String[] arrayOfString = paramContext.getFilesDir().list();
        if (arrayOfString == null) {
          return;
        }
        i = 0;
        int j = arrayOfString.length;
        if (i < j)
        {
          Object localObject = arrayOfString[i];
          if (!((String)localObject).endsWith(".urlimage")) {
            break label118;
          }
          localObject = new File(paramContext.getFilesDir().getAbsolutePath() + '/' + (String)localObject);
          if (System.currentTimeMillis() <= ((File)localObject).lastModified() + 604800000L) {
            break label118;
          }
          ((File)localObject).delete();
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
      return;
      label118:
      i += 1;
    }
  }
  
  public static int copyStream(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte['Ѐ'];
    int i = 0;
    for (;;)
    {
      int j = paramInputStream.read(arrayOfByte);
      if (j == -1) {
        break;
      }
      paramOutputStream.write(arrayOfByte, 0, j);
      i += j;
    }
    return i;
  }
  
  private static void executeTask(AsyncTask<Void, Void, Void> paramAsyncTask)
  {
    if (Build.VERSION.SDK_INT < 11)
    {
      paramAsyncTask.execute(new Void[0]);
      return;
    }
    executeTaskHoneycomb(paramAsyncTask);
  }
  
  @TargetApi(11)
  private static void executeTaskHoneycomb(AsyncTask<Void, Void, Void> paramAsyncTask)
  {
    paramAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
  }
  
  public static String getFilenameForUrl(String paramString)
  {
    return "" + paramString.hashCode() + ".urlimage";
  }
  
  private static int getHeapSize(Context paramContext)
  {
    return ((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass() * 1024 * 1024;
  }
  
  private static boolean isNullOrEmpty(CharSequence paramCharSequence)
  {
    if ((paramCharSequence != null) && (!paramCharSequence.equals("")) && (!paramCharSequence.equals("null"))) {
      return paramCharSequence.equals("NULL");
    }
    return true;
  }
  
  private static Drawable loadDrawableFromStream(Context paramContext, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    prepareResources(paramContext);
    try
    {
      paramContext = new BitmapFactory.Options();
      paramContext.inJustDecodeBounds = true;
      FileInputStream localFileInputStream = new FileInputStream(paramString2);
      BitmapFactory.decodeStream(localFileInputStream, null, paramContext);
      localFileInputStream.close();
      paramString2 = new FileInputStream(paramString2);
      int i = 0;
      while ((paramContext.outWidth >> i > paramInt1) || (paramContext.outHeight >> i > paramInt2))
      {
        Log.v("UrlImageViewHelper", "downsampling");
        i += 1;
      }
      paramContext = new BitmapFactory.Options();
      paramContext.inSampleSize = (1 << i);
      paramContext = BitmapFactory.decodeStream(paramString2, null, paramContext);
      paramContext = new ZombieDrawable(paramString1, new BitmapDrawable(mResources, paramContext));
      return paramContext;
    }
    catch (IOException paramContext) {}
    return null;
  }
  
  private static void prepareResources(Context paramContext)
  {
    if (mMetrics != null) {
      return;
    }
    mMetrics = new DisplayMetrics();
    ((Activity)paramContext).getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    mResources = new Resources(paramContext.getAssets(), mMetrics, paramContext.getResources().getConfiguration());
  }
  
  private static void setUrlDrawable(Context paramContext, ImageView paramImageView, String paramString, int paramInt, long paramLong)
  {
    Drawable localDrawable = null;
    if (paramInt != 0) {
      localDrawable = paramImageView.getResources().getDrawable(paramInt);
    }
    setUrlDrawable(paramContext, paramImageView, paramString, localDrawable, paramLong, null);
  }
  
  private static void setUrlDrawable(Context paramContext, ImageView paramImageView, final String paramString, final Drawable paramDrawable, long paramLong, final UrlImageViewCallback paramUrlImageViewCallback)
  {
    cleanup(paramContext);
    if (isNullOrEmpty(paramString))
    {
      if (paramImageView != null) {
        paramImageView.setImageDrawable(paramDrawable);
      }
      return;
    }
    final Object localObject = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    final int i = ((Display)localObject).getWidth();
    final int j = ((Display)localObject).getHeight();
    if (mDeadCache == null) {
      mDeadCache = new UrlLruCache(getHeapSize(paramContext) / 8);
    }
    localObject = (BitmapDrawable)mDeadCache.remove(paramString);
    boolean bool;
    if (localObject != null) {
      if (mAllCache.contains(localObject))
      {
        bool = false;
        Assert.assertTrue(bool);
      }
    }
    for (localObject = new ZombieDrawable(paramString, (BitmapDrawable)localObject);; localObject = (Drawable)mLiveCache.get(paramString))
    {
      if (localObject == null) {
        break label177;
      }
      if (paramImageView != null) {
        paramImageView.setImageDrawable((Drawable)localObject);
      }
      if (paramUrlImageViewCallback != null) {
        paramUrlImageViewCallback.onLoaded(paramImageView, (Drawable)localObject, paramString, true);
      }
      return;
      bool = true;
      break;
    }
    label177:
    localObject = paramContext.getFileStreamPath(getFilenameForUrl(paramString)).getAbsolutePath();
    if (paramImageView != null) {
      paramImageView.setImageDrawable(paramDrawable);
    }
    if (paramImageView != null) {
      mPendingViews.put(paramImageView, paramString);
    }
    final ArrayList localArrayList = (ArrayList)mPendingDownloads.get(paramString);
    if (localArrayList != null)
    {
      if (paramImageView != null) {
        localArrayList.add(paramImageView);
      }
      return;
    }
    localArrayList = new ArrayList();
    if (paramImageView != null) {
      localArrayList.add(paramImageView);
    }
    mPendingDownloads.put(paramString, localArrayList);
    if (i <= 0)
    {
      i = Integer.MAX_VALUE;
      if (j > 0) {
        break label381;
      }
      j = Integer.MAX_VALUE;
    }
    label381:
    for (;;)
    {
      paramImageView = new Loader(paramContext)
      {
        public void run()
        {
          try
          {
            this.result = UrlImageViewHelper.-wrap0(this.val$context, paramString, localObject, i, j);
            return;
          }
          catch (Exception localException) {}
        }
      };
      paramDrawable = new Runnable()
      {
        public void run()
        {
          Assert.assertEquals(Looper.myLooper(), Looper.getMainLooper());
          Object localObject2 = this.val$loader.result;
          Object localObject1 = localObject2;
          if (localObject2 == null) {
            localObject1 = paramDrawable;
          }
          UrlImageViewHelper.-get3().remove(paramString);
          UrlImageViewHelper.-get2().put(paramString, localObject1);
          localObject2 = localArrayList.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            ImageView localImageView = (ImageView)((Iterator)localObject2).next();
            String str = (String)UrlImageViewHelper.-get4().get(localImageView);
            if (paramString.equals(str))
            {
              UrlImageViewHelper.-get4().remove(localImageView);
              if (localObject1 != null)
              {
                localImageView.setImageDrawable((Drawable)localObject1);
                if (paramUrlImageViewCallback != null) {
                  paramUrlImageViewCallback.onLoaded(localImageView, this.val$loader.result, paramString, false);
                }
              }
            }
          }
        }
      };
      paramUrlImageViewCallback = new File((String)localObject);
      if (!paramUrlImageViewCallback.exists()) {
        break label386;
      }
      if (paramLong != 2147483647L) {}
      try
      {
        if (System.currentTimeMillis() >= paramUrlImageViewCallback.lastModified() + paramLong) {
          break label386;
        }
        executeTask(new AsyncTask()
        {
          protected Void doInBackground(Void[] paramAnonymousArrayOfVoid)
          {
            this.val$loader.run();
            return null;
          }
          
          protected void onPostExecute(Void paramAnonymousVoid)
          {
            paramDrawable.run();
          }
        });
        return;
      }
      catch (Exception paramUrlImageViewCallback) {}
      break;
    }
    label386:
    mDownloader.download(paramContext, paramString, (String)localObject, paramImageView, paramDrawable);
  }
  
  public static void setUrlDrawable(ImageView paramImageView, String paramString, int paramInt, long paramLong)
  {
    setUrlDrawable(paramImageView.getContext(), paramImageView, paramString, paramInt, paramLong);
  }
  
  private static abstract class Loader
    implements Runnable
  {
    public Drawable result;
  }
  
  public static abstract interface RequestPropertiesCallback
  {
    public abstract ArrayList<NameValuePair> getHeadersForRequest(Context paramContext, String paramString);
  }
  
  public static abstract interface UrlDownloader
  {
    public abstract void download(Context paramContext, String paramString1, String paramString2, Runnable paramRunnable1, Runnable paramRunnable2);
  }
  
  private static class ZombieDrawable
    extends WrapperDrawable
  {
    String mUrl;
    
    public ZombieDrawable(String paramString, BitmapDrawable paramBitmapDrawable)
    {
      super();
      this.mUrl = paramString;
      UrlImageViewHelper.-get0().add(paramBitmapDrawable);
      UrlImageViewHelper.-get1().remove(paramString);
      UrlImageViewHelper.-get2().put(paramString, this);
    }
    
    protected void finalize()
      throws Throwable
    {
      super.finalize();
      UrlImageViewHelper.-get1().put(this.mUrl, this.mDrawable);
      UrlImageViewHelper.-get0().remove(this.mDrawable);
      UrlImageViewHelper.-get2().remove(this.mUrl);
      System.gc();
    }
  }
}

