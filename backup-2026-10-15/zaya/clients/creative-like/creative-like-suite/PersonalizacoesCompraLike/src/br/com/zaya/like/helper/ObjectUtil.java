package br.com.zaya.like.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObjectUtil {
  public static boolean isNull(Object object) {
    return (object == null);
  }
  
  public static boolean isNotNull(Object object) {
    return !isNull(object);
  }
  
  public static boolean nonNull(Object object) {
    return !isNull(object);
  }
  
  public static void requireNonNull(Object object, String message) {
    if (isNull(object))
      throw new IllegalArgumentException(message); 
  }
  
  public static boolean isEquals(Object target, Object model) {
    if (target == null && model == null)
      return true; 
    return (target != null && model != null) ? target.equals(model) : false;
  }
  
  public static boolean isNotEquals(Object target, Object model) {
    return !isEquals(target, model);
  }
  
  public static boolean isEmpty(Object[] array) {
    return (isNull(array) || array.length == 0);
  }
  
  public static boolean isEmpty(Collection<?> collection) {
    return (isNull(collection) || collection.size() == 0);
  }
  
  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }
  
  public static final <T> List<T> toList(Collection<T> collection) {
    return (collection != null && !collection.isEmpty()) ? new ArrayList<>(collection) : new ArrayList<>();
  }
}