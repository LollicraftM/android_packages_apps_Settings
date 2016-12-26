package com.android.settings.lollicraft;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Resources;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.provider.SearchIndexableResource;
import android.text.TextUtils;
import android.util.Log;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settings.search.Indexable.SearchIndexProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedDeviceInfoSettings
  extends SettingsPreferenceFragment
  implements Indexable
{
  public static final int[] GL_INFO = { 7936, 7937, 7938, 7939, 35724 };
  public static final int[] GL_STRINGS = { 2131493866, 2131493867, 2131493868, 2131493869, 2131493870 };
  public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new BaseSearchIndexProvider()
  {
    public List<SearchIndexableResource> getXmlResourcesToIndex(Context paramAnonymousContext, boolean paramAnonymousBoolean)
    {
      paramAnonymousContext = new SearchIndexableResource(paramAnonymousContext);
      paramAnonymousContext.xmlResId = 2131230750;
      return Arrays.asList(new SearchIndexableResource[] { paramAnonymousContext });
    }
  };
  
  /* Error */
  private String getCpuFeatures()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 4
    //   8: aconst_null
    //   9: astore_2
    //   10: aconst_null
    //   11: astore 5
    //   13: new 40	java/io/BufferedReader
    //   16: dup
    //   17: new 42	java/io/FileReader
    //   20: dup
    //   21: ldc 44
    //   23: invokespecial 47	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   26: sipush 256
    //   29: invokespecial 50	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   32: astore_1
    //   33: aload_1
    //   34: invokevirtual 53	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   37: invokevirtual 58	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   40: astore_2
    //   41: aload_2
    //   42: ifnull +63 -> 105
    //   45: aload_2
    //   46: ldc 60
    //   48: invokevirtual 64	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   51: ifeq -18 -> 33
    //   54: aload_2
    //   55: ldc 66
    //   57: invokevirtual 70	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   60: iconst_1
    //   61: aaload
    //   62: invokevirtual 73	java/lang/String:trim	()Ljava/lang/String;
    //   65: astore 5
    //   67: aload 4
    //   69: astore_2
    //   70: aload_1
    //   71: ifnull +10 -> 81
    //   74: aload_1
    //   75: invokevirtual 76	java/io/BufferedReader:close	()V
    //   78: aload 4
    //   80: astore_2
    //   81: aload_2
    //   82: ifnull +20 -> 102
    //   85: aload_2
    //   86: athrow
    //   87: astore_1
    //   88: aload_0
    //   89: invokevirtual 80	com/android/settings/lollicraft/AdvancedDeviceInfoSettings:getResources	()Landroid/content/res/Resources;
    //   92: ldc 81
    //   94: invokevirtual 87	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   97: areturn
    //   98: astore_2
    //   99: goto -18 -> 81
    //   102: aload 5
    //   104: areturn
    //   105: aload 6
    //   107: astore_2
    //   108: aload_1
    //   109: ifnull +10 -> 119
    //   112: aload_1
    //   113: invokevirtual 76	java/io/BufferedReader:close	()V
    //   116: aload 6
    //   118: astore_2
    //   119: aload_2
    //   120: ifnull +9 -> 129
    //   123: aload_2
    //   124: athrow
    //   125: astore_2
    //   126: goto -7 -> 119
    //   129: goto -41 -> 88
    //   132: astore_2
    //   133: aload 5
    //   135: astore_1
    //   136: aload_2
    //   137: athrow
    //   138: astore 4
    //   140: aload_2
    //   141: astore_3
    //   142: aload 4
    //   144: astore_2
    //   145: aload_3
    //   146: astore 4
    //   148: aload_1
    //   149: ifnull +10 -> 159
    //   152: aload_1
    //   153: invokevirtual 76	java/io/BufferedReader:close	()V
    //   156: aload_3
    //   157: astore 4
    //   159: aload 4
    //   161: ifnull +25 -> 186
    //   164: aload 4
    //   166: athrow
    //   167: aload_3
    //   168: astore 4
    //   170: aload_3
    //   171: aload_1
    //   172: if_acmpeq -13 -> 159
    //   175: aload_3
    //   176: aload_1
    //   177: invokevirtual 91	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   180: aload_3
    //   181: astore 4
    //   183: goto -24 -> 159
    //   186: aload_2
    //   187: athrow
    //   188: astore 4
    //   190: aload_2
    //   191: astore_1
    //   192: aload 4
    //   194: astore_2
    //   195: goto -50 -> 145
    //   198: astore_2
    //   199: goto -54 -> 145
    //   202: astore_2
    //   203: goto -67 -> 136
    //   206: astore_1
    //   207: goto -119 -> 88
    //   210: astore_1
    //   211: aload_3
    //   212: ifnonnull -45 -> 167
    //   215: aload_1
    //   216: astore 4
    //   218: goto -59 -> 159
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	221	0	this	AdvancedDeviceInfoSettings
    //   32	43	1	localBufferedReader	java.io.BufferedReader
    //   87	26	1	localIOException1	IOException
    //   135	57	1	localObject1	Object
    //   206	1	1	localIOException2	IOException
    //   210	6	1	localThrowable1	Throwable
    //   9	77	2	localObject2	Object
    //   98	1	2	localThrowable2	Throwable
    //   107	17	2	localObject3	Object
    //   125	1	2	localThrowable3	Throwable
    //   132	9	2	localThrowable4	Throwable
    //   144	51	2	localObject4	Object
    //   198	1	2	localObject5	Object
    //   202	1	2	localThrowable5	Throwable
    //   4	208	3	localObject6	Object
    //   6	73	4	localObject7	Object
    //   138	5	4	localObject8	Object
    //   146	36	4	localObject9	Object
    //   188	5	4	localObject10	Object
    //   216	1	4	localObject11	Object
    //   11	123	5	str	String
    //   1	116	6	localObject12	Object
    // Exception table:
    //   from	to	target	type
    //   74	78	87	java/io/IOException
    //   85	87	87	java/io/IOException
    //   112	116	87	java/io/IOException
    //   123	125	87	java/io/IOException
    //   74	78	98	java/lang/Throwable
    //   112	116	125	java/lang/Throwable
    //   13	33	132	java/lang/Throwable
    //   136	138	138	finally
    //   13	33	188	finally
    //   33	41	198	finally
    //   45	67	198	finally
    //   33	41	202	java/lang/Throwable
    //   45	67	202	java/lang/Throwable
    //   152	156	206	java/io/IOException
    //   164	167	206	java/io/IOException
    //   175	180	206	java/io/IOException
    //   186	188	206	java/io/IOException
    //   152	156	210	java/lang/Throwable
  }
  
  private String getCpuInfo()
  {
    try
    {
      String str = readLine("/proc/cpuinfo");
      if (str != null)
      {
        str = str.split(":")[1].trim();
        return str;
      }
    }
    catch (IOException localIOException) {}
    return getResources().getString(2131493915);
  }
  
  /* Error */
  private String getCpuProcessor()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore_3
    //   5: aconst_null
    //   6: astore 4
    //   8: aconst_null
    //   9: astore_2
    //   10: aconst_null
    //   11: astore 6
    //   13: new 40	java/io/BufferedReader
    //   16: dup
    //   17: new 42	java/io/FileReader
    //   20: dup
    //   21: ldc 44
    //   23: invokespecial 47	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   26: invokespecial 99	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   29: astore_1
    //   30: aload_1
    //   31: invokevirtual 53	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   34: astore_2
    //   35: aload_2
    //   36: ifnull +74 -> 110
    //   39: aload_2
    //   40: ldc 101
    //   42: invokevirtual 105	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   45: ifeq -15 -> 30
    //   48: ldc 107
    //   50: invokestatic 113	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   53: aload_2
    //   54: invokevirtual 117	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   57: astore_2
    //   58: aload_2
    //   59: invokevirtual 123	java/util/regex/Matcher:matches	()Z
    //   62: ifeq -32 -> 30
    //   65: aload_2
    //   66: iconst_1
    //   67: invokevirtual 126	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   70: astore 5
    //   72: aload 4
    //   74: astore_2
    //   75: aload_1
    //   76: ifnull +10 -> 86
    //   79: aload_1
    //   80: invokevirtual 76	java/io/BufferedReader:close	()V
    //   83: aload 4
    //   85: astore_2
    //   86: aload_2
    //   87: ifnull +20 -> 107
    //   90: aload_2
    //   91: athrow
    //   92: astore_1
    //   93: aload_0
    //   94: invokevirtual 80	com/android/settings/lollicraft/AdvancedDeviceInfoSettings:getResources	()Landroid/content/res/Resources;
    //   97: ldc 81
    //   99: invokevirtual 87	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   102: areturn
    //   103: astore_2
    //   104: goto -18 -> 86
    //   107: aload 5
    //   109: areturn
    //   110: aload 5
    //   112: astore_2
    //   113: aload_1
    //   114: ifnull +10 -> 124
    //   117: aload_1
    //   118: invokevirtual 76	java/io/BufferedReader:close	()V
    //   121: aload 5
    //   123: astore_2
    //   124: aload_2
    //   125: ifnull +9 -> 134
    //   128: aload_2
    //   129: athrow
    //   130: astore_2
    //   131: goto -7 -> 124
    //   134: goto -41 -> 93
    //   137: astore_2
    //   138: aload 6
    //   140: astore_1
    //   141: aload_2
    //   142: athrow
    //   143: astore 4
    //   145: aload_2
    //   146: astore_3
    //   147: aload 4
    //   149: astore_2
    //   150: aload_3
    //   151: astore 4
    //   153: aload_1
    //   154: ifnull +10 -> 164
    //   157: aload_1
    //   158: invokevirtual 76	java/io/BufferedReader:close	()V
    //   161: aload_3
    //   162: astore 4
    //   164: aload 4
    //   166: ifnull +25 -> 191
    //   169: aload 4
    //   171: athrow
    //   172: aload_3
    //   173: astore 4
    //   175: aload_3
    //   176: aload_1
    //   177: if_acmpeq -13 -> 164
    //   180: aload_3
    //   181: aload_1
    //   182: invokevirtual 91	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   185: aload_3
    //   186: astore 4
    //   188: goto -24 -> 164
    //   191: aload_2
    //   192: athrow
    //   193: astore 4
    //   195: aload_2
    //   196: astore_1
    //   197: aload 4
    //   199: astore_2
    //   200: goto -50 -> 150
    //   203: astore_2
    //   204: goto -54 -> 150
    //   207: astore_2
    //   208: goto -67 -> 141
    //   211: astore_1
    //   212: goto -119 -> 93
    //   215: astore_1
    //   216: aload_3
    //   217: ifnonnull -45 -> 172
    //   220: aload_1
    //   221: astore 4
    //   223: goto -59 -> 164
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	226	0	this	AdvancedDeviceInfoSettings
    //   29	51	1	localBufferedReader	java.io.BufferedReader
    //   92	26	1	localIOException1	IOException
    //   140	57	1	localObject1	Object
    //   211	1	1	localIOException2	IOException
    //   215	6	1	localThrowable1	Throwable
    //   9	82	2	localObject2	Object
    //   103	1	2	localThrowable2	Throwable
    //   112	17	2	str1	String
    //   130	1	2	localThrowable3	Throwable
    //   137	9	2	localThrowable4	Throwable
    //   149	51	2	localObject3	Object
    //   203	1	2	localObject4	Object
    //   207	1	2	localThrowable5	Throwable
    //   4	213	3	localObject5	Object
    //   6	78	4	localObject6	Object
    //   143	5	4	localObject7	Object
    //   151	36	4	localObject8	Object
    //   193	5	4	localObject9	Object
    //   221	1	4	localObject10	Object
    //   1	121	5	str2	String
    //   11	128	6	localObject11	Object
    // Exception table:
    //   from	to	target	type
    //   79	83	92	java/io/IOException
    //   90	92	92	java/io/IOException
    //   117	121	92	java/io/IOException
    //   128	130	92	java/io/IOException
    //   79	83	103	java/lang/Throwable
    //   117	121	130	java/lang/Throwable
    //   13	30	137	java/lang/Throwable
    //   141	143	143	finally
    //   13	30	193	finally
    //   30	35	203	finally
    //   39	72	203	finally
    //   30	35	207	java/lang/Throwable
    //   39	72	207	java/lang/Throwable
    //   157	161	211	java/io/IOException
    //   169	172	211	java/io/IOException
    //   180	185	211	java/io/IOException
    //   191	193	211	java/io/IOException
    //   157	161	215	java/lang/Throwable
  }
  
  private String getMemInfo()
  {
    try
    {
      Object localObject = readLine("/proc/meminfo");
      if (localObject != null)
      {
        localObject = ((String)localObject).split("\\s+");
        if (localObject.length == 3)
        {
          localObject = Long.parseLong(localObject[1]) / 1024L + " MB";
          return (String)localObject;
        }
      }
    }
    catch (IOException localIOException) {}
    return getResources().getString(2131493915);
  }
  
  private ArrayList<String> getOpenGLESInformation()
  {
    ArrayList localArrayList = new ArrayList(GL_INFO.length);
    if (!isOpenGLES20Supported()) {
      return localArrayList;
    }
    EGLDisplay localEGLDisplay = EGL14.eglGetDisplay(0);
    Object localObject1 = new int[2];
    EGL14.eglInitialize(localEGLDisplay, (int[])localObject1, 0, (int[])localObject1, 1);
    localObject1 = new EGLConfig[1];
    Object localObject2 = new int[1];
    EGL14.eglChooseConfig(localEGLDisplay, new int[] { 12351, 12430, 12329, 0, 12352, 4, 12339, 1, 12344 }, 0, (EGLConfig[])localObject1, 0, 1, (int[])localObject2, 0);
    if (localObject2[0] == 0) {
      Log.w("AdvancedDeviceInfoSettings", "no config found! PANIC!");
    }
    localObject2 = localObject1[0];
    localObject1 = EGL14.eglCreatePbufferSurface(localEGLDisplay, (EGLConfig)localObject2, new int[] { 12375, 64, 12374, 64, 12344 }, 0);
    localObject2 = EGL14.eglCreateContext(localEGLDisplay, (EGLConfig)localObject2, EGL14.EGL_NO_CONTEXT, new int[] { 12440, 2, 12344 }, 0);
    EGL14.eglMakeCurrent(localEGLDisplay, (EGLSurface)localObject1, (EGLSurface)localObject1, (EGLContext)localObject2);
    int[] arrayOfInt = GL_INFO;
    int i = 0;
    int j = arrayOfInt.length;
    while (i < j)
    {
      localArrayList.add(GLES20.glGetString(arrayOfInt[i]));
      i += 1;
    }
    EGL14.eglMakeCurrent(localEGLDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
    EGL14.eglDestroySurface(localEGLDisplay, (EGLSurface)localObject1);
    EGL14.eglDestroyContext(localEGLDisplay, (EGLContext)localObject2);
    EGL14.eglTerminate(localEGLDisplay);
    return localArrayList;
  }
  
  private boolean isOpenGLES20Supported()
  {
    boolean bool = false;
    ConfigurationInfo localConfigurationInfo = ((ActivityManager)getSystemService("activity")).getDeviceConfigurationInfo();
    if (localConfigurationInfo == null) {
      return false;
    }
    if ((localConfigurationInfo.reqGlEsVersion & 0xFFFF0000) >> 16 >= 2) {
      bool = true;
    }
    return bool;
  }
  
  /* Error */
  private static String readLine(String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 42	java/io/FileReader
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 47	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   12: astore 5
    //   14: aconst_null
    //   15: astore_1
    //   16: aconst_null
    //   17: astore 4
    //   19: new 40	java/io/BufferedReader
    //   22: dup
    //   23: aload 5
    //   25: sipush 256
    //   28: invokespecial 50	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   31: astore_0
    //   32: aload_0
    //   33: invokevirtual 53	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   36: astore 4
    //   38: aload_3
    //   39: astore_1
    //   40: aload_0
    //   41: ifnull +9 -> 50
    //   44: aload_0
    //   45: invokevirtual 76	java/io/BufferedReader:close	()V
    //   48: aload_3
    //   49: astore_1
    //   50: aload_1
    //   51: ifnull +17 -> 68
    //   54: aload_1
    //   55: athrow
    //   56: astore_0
    //   57: aload 5
    //   59: invokevirtual 253	java/io/FileReader:close	()V
    //   62: aload_0
    //   63: athrow
    //   64: astore_1
    //   65: goto -15 -> 50
    //   68: aload 5
    //   70: invokevirtual 253	java/io/FileReader:close	()V
    //   73: aload 4
    //   75: areturn
    //   76: astore_1
    //   77: aload 4
    //   79: astore_0
    //   80: aload_1
    //   81: athrow
    //   82: astore_3
    //   83: aload_1
    //   84: astore_2
    //   85: aload_3
    //   86: astore_1
    //   87: aload_2
    //   88: astore_3
    //   89: aload_0
    //   90: ifnull +9 -> 99
    //   93: aload_0
    //   94: invokevirtual 76	java/io/BufferedReader:close	()V
    //   97: aload_2
    //   98: astore_3
    //   99: aload_3
    //   100: ifnull +22 -> 122
    //   103: aload_3
    //   104: athrow
    //   105: aload_2
    //   106: astore_3
    //   107: aload_2
    //   108: aload_0
    //   109: if_acmpeq -10 -> 99
    //   112: aload_2
    //   113: aload_0
    //   114: invokevirtual 91	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   117: aload_2
    //   118: astore_3
    //   119: goto -20 -> 99
    //   122: aload_1
    //   123: athrow
    //   124: astore_3
    //   125: aload_1
    //   126: astore_0
    //   127: aload_3
    //   128: astore_1
    //   129: goto -42 -> 87
    //   132: astore_1
    //   133: goto -46 -> 87
    //   136: astore_1
    //   137: goto -57 -> 80
    //   140: astore_0
    //   141: goto -84 -> 57
    //   144: astore_0
    //   145: aload_2
    //   146: ifnonnull -41 -> 105
    //   149: aload_0
    //   150: astore_3
    //   151: goto -52 -> 99
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	154	0	paramString	String
    //   15	40	1	localObject1	Object
    //   64	1	1	localThrowable1	Throwable
    //   76	8	1	localThrowable2	Throwable
    //   86	43	1	localObject2	Object
    //   132	1	1	localObject3	Object
    //   136	1	1	localThrowable3	Throwable
    //   1	145	2	localObject4	Object
    //   3	46	3	localObject5	Object
    //   82	4	3	localObject6	Object
    //   88	31	3	localObject7	Object
    //   124	4	3	localObject8	Object
    //   150	1	3	str1	String
    //   17	61	4	str2	String
    //   12	57	5	localFileReader	java.io.FileReader
    // Exception table:
    //   from	to	target	type
    //   44	48	56	finally
    //   54	56	56	finally
    //   44	48	64	java/lang/Throwable
    //   19	32	76	java/lang/Throwable
    //   80	82	82	finally
    //   19	32	124	finally
    //   32	38	132	finally
    //   32	38	136	java/lang/Throwable
    //   93	97	140	finally
    //   103	105	140	finally
    //   112	117	140	finally
    //   122	124	140	finally
    //   93	97	144	java/lang/Throwable
  }
  
  private void setStringSummary(String paramString1, String paramString2)
  {
    try
    {
      findPreference(paramString1).setSummary(paramString2);
      return;
    }
    catch (RuntimeException paramString2)
    {
      findPreference(paramString1).setSummary(getResources().getString(2131493915));
    }
  }
  
  public String formatKernelVersion(String paramString)
  {
    int i = 1;
    Matcher localMatcher = Pattern.compile("Linux version (\\S+) \\((\\S+?)\\) (?:\\(gcc.+? \\)) (#\\d+) (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)").matcher(paramString);
    if (!localMatcher.matches())
    {
      Log.e("AdvancedDeviceInfoSettings", "Regex did not match: " + paramString);
      i = 0;
    }
    while (i == 0)
    {
      return getResources().getString(2131493915);
      if (localMatcher.groupCount() < 4)
      {
        Log.e("AdvancedDeviceInfoSettings", "Regex match only returned " + localMatcher.groupCount() + " groups");
        i = 0;
      }
    }
    return localMatcher.group(1) + "\n" + localMatcher.group(2) + " " + localMatcher.group(3) + "\n" + localMatcher.group(4);
  }
  
  public String getFormattedKernelVersion()
  {
    try
    {
      String str = formatKernelVersion(readLine("/proc/version"));
      return str;
    }
    catch (IOException localIOException)
    {
      Log.e("AdvancedDeviceInfoSettings", "IO Exception when getting kernel version for Device Info screen", localIOException);
    }
    return getResources().getString(2131493915);
  }
  
  protected int getMetricsCategory()
  {
    return 40;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(2131230750);
    setStringSummary("kernel_version", getFormattedKernelVersion());
    setStringSummary("device_memory", getMemInfo());
    setStringSummary("device_cpu_hardware", getCpuProcessor());
    setStringSummary("device_cpu_type", getCpuInfo());
    setStringSummary("device_cpu_features", getCpuFeatures());
    paramBundle = (PreferenceCategory)findPreference("category_device_gpu");
    ArrayList localArrayList = getOpenGLESInformation();
    if (localArrayList.size() != 0)
    {
      int i = 0;
      while (i < localArrayList.size())
      {
        String str = (String)localArrayList.get(i);
        if (!TextUtils.isEmpty(str))
        {
          Preference localPreference = new Preference(getActivity());
          localPreference.setTitle(GL_STRINGS[i]);
          localPreference.setSummary(str);
          paramBundle.addPreference(localPreference);
        }
        i += 1;
      }
    }
  }
}

