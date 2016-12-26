package com.koushikdutta.urlimageviewhelper;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class SoftReferenceHashTable<K, V>
{
  Hashtable<K, SoftReference<V>> mTable = new Hashtable();
  
  public V get(K paramK)
  {
    Object localObject = (SoftReference)this.mTable.get(paramK);
    if (localObject == null) {
      return null;
    }
    localObject = ((SoftReference)localObject).get();
    if (localObject == null) {
      this.mTable.remove(paramK);
    }
    return (V)localObject;
  }
  
  public V put(K paramK, V paramV)
  {
    paramK = (SoftReference)this.mTable.put(paramK, new SoftReference(paramV));
    if (paramK == null) {
      return null;
    }
    return (V)paramK.get();
  }
  
  public V remove(K paramK)
  {
    paramK = (SoftReference)this.mTable.remove(paramK);
    if (paramK == null) {
      return null;
    }
    return (V)paramK.get();
  }
}

