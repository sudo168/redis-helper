package net.ewant.redis.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ReflectUtil {

	public static Method getReadMethod(Class<?> clazz,String field) throws Exception {
		PropertyDescriptor descriptor = new PropertyDescriptor(field, clazz);
		Method writeMethod = descriptor.getReadMethod();
		return writeMethod;
	}
	
	public static Method getWriteMethod(Class<?> clazz,String field) throws Exception {
		PropertyDescriptor descriptor = new PropertyDescriptor(field, clazz);
		Method writeMethod = descriptor.getWriteMethod();
		return writeMethod;
	}
	
	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		if (obj == null || fieldName == null) {
			return null;
		}
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) {
		Object value = null;
		try {
			Field field = getFieldByFieldName(obj, fieldName);
			if (field != null) {
				if (field.isAccessible()) {
					value = field.get(obj);
				} else {
					field.setAccessible(true);
					value = field.get(obj);
					field.setAccessible(false);
				}
			}
		} catch (Exception e) {
		}
		return value;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
		Object value = null;
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field[] fields = superClass.getDeclaredFields();
				for (Field f : fields) {
					if (fieldType.isAssignableFrom(f.getType())) {
						if (f.isAccessible()) {
							value = f.get(obj);
							break;
						} else {
							f.setAccessible(true);
							value = f.get(obj);
							f.setAccessible(false);
							break;
						}
					}
				}
				if (value != null) {
					break;
				}
			} catch (Exception e) {
			}
		}
		return (T) value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static boolean setValueByFieldName(Object obj, String fieldName,
			Object value) {
		try {
			Field field = getFieldByFieldName(obj, fieldName);
			if (field.isAccessible()) {//获取此对象的 accessible 标志的值。 
				field.set(obj, value);//将指定对象变量上此 Field 对象表示的字段设置为指定的新值
			} else {
				field.setAccessible(true);
				field.set(obj, value);
				field.setAccessible(false);
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	/**  
	  * 判断一个类是否为基本数据类型。  
	  * @param clazz 要判断的类。  
	  * @return true 表示为基本数据类型。  
	  */ 
	 public static boolean isBaseDataType(Class<?> clazz) {   
	     return (  
 		 clazz.isPrimitive() ||
	         clazz.equals(String.class) ||
	         clazz.equals(Integer.class)||   
	         clazz.equals(Long.class) ||   
	         clazz.equals(Double.class) ||   
	         clazz.equals(Float.class) ||   
	         clazz.equals(Boolean.class) ||   
	         clazz.equals(Character.class) ||  
	         clazz.equals(Short.class) || 
	         clazz.equals(Byte.class) ||
	         clazz.equals(BigDecimal.class) ||   
	         clazz.equals(BigInteger.class)   
	     ); 
	}
	 
	public static <T> T getEnum(Class<T> enumType, String name) throws Exception {  
		T[] enumConstants = enumType.getEnumConstants();
		for (T t : enumConstants) {
			if(t.toString().equals(name) || ((Enum<?>) t).name().equals(name)){
				return t;
			}
		}
		for (T t : enumConstants) {
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field field : fields) {
				Object value = null;
				if (field.isAccessible()) {
					value  = field.get(t);
				} else {
					field.setAccessible(true);
					value = field.get(t);
					field.setAccessible(false);
				}
				if(value != null && value.toString().equals(name)){
					return t;
				}
			}
		}
		return null;
	} 
	/**
	 * 通过在枚举中定义的位置获取枚举
	 * @param enumType
	 * @param ordinal 从0开始
	 * @return
	 * @throws Exception
	 */
	public static <T> T getEnum(Class<T> enumType, int ordinal) throws Exception {  
		return enumType.getEnumConstants()[ordinal];
	} 
}
