package net.ewant.redis.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SerializeUtils {

	private static final Serialization serialization = new SimpleJsonSerialization();
	
	public static byte[] serialize(Object message) {
		try {
			return serialization.serialize(message);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object deserialize(byte[] bs) {
		try {
			return serialization.deserialize(bs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T deserialize(byte[] bs , Class<T> type) {
		try {
			return serialization.deserialize(bs,type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public interface Serialization {

		byte[] serialize(Object object) throws Exception;

		Object deserialize(byte[] bs) throws Exception;
		
		<T> T deserialize(byte[] bs, Class<T> type) throws Exception;
		
	}
	
	public static final class SimpleJsonSerialization implements Serialization{

		@Override
		public byte[] serialize(Object object) throws Exception {
			String attrToString = objectAttrToString(object);
			return attrToString == null ? null : attrToString.getBytes();
		}

		@Override
		public Object deserialize(byte[] bs) throws Exception {
			if (bs != null && bs.length > 0) {
				return stringAttrToObject(new String(bs));
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T deserialize(byte[] bs, Class<T> type) throws Exception {
			Object object = deserialize(bs);
			if (object != null && type.isAssignableFrom(object.getClass()) ) {
				return (T) object;
			}
			return JSON.parseObject(bs, type);
		}
		
		private String objectAttrToString(Object attr) throws Exception{
			if (attr == null) {
				throw new NullPointerException("参数值为空.");
			}
			String value = null ;
			if(attr instanceof String){
				value = attr.toString();
			}else if(attr instanceof Enum){
				value = ((Enum<?>) attr).name();
			}else{
				value = JSON.toJSONString(attr);
			}
			if (value.equals("[]") || value.equals("{}")
					|| value.equals("[{}]") || value.equals("{{}}") || value.equals("{[]}") || value.equals("[[]]")) {
				// throw new NullPointerException("空数据不需要存储.");
				return null;
			}
			String mask = "";
			if ((mask = generaValueMask(attr)).length() > 1) {
				value += "->" + mask.substring(1);
			}
			return value;
		}
		
		private String generaValueMask(Object attr){
			Class<?> clazz = attr.getClass();
			String res = "";
			if (ReflectUtil.isBaseDataType(clazz)) {// -> 基本类型
				return res += ":" + clazz.getName();
			}
			if (clazz.isArray()) {// ->( 数组
				Object[] objects = ((Object[]) attr);
				if (objects.length == 0) {
					return "";
				}
				res += ":[" + clazz.getName();
				return res += generaValueMask(objects[0]);
			}
			if (Collection.class.isAssignableFrom(clazz)) {// ->[ 单列集合
				Collection<?> item = (Collection<?>) attr;
				if (item.isEmpty()) {
					return "";
				}
				res += ":" + clazz.getName();
				for (Object object : item) {
					if (object != null) {
						return res += generaValueMask(object);
					}
				}
			}else if (Map.class.isAssignableFrom(clazz)) {// ->{K&V 双列集合
				Map<?,?> item = (Map<?,?>) attr;
				if (item.isEmpty()) {
					return "";
				}
				res += ":" + clazz.getName();
				for (Object k : item.keySet()) {
					Object v = null;
					if (k != null && (v = item.get(k)) != null) {
						res += ":";
						String mask = generaValueMask(k);
						if (mask.length() > 1) {
							res += mask.substring(1);
						}
						res += ":";
						mask = generaValueMask(v);
						if (mask.length() > 1) {
							res += mask.substring(1);
						}
						return res;
					}
				}
			}else{// ->$ 普通java bean
				res += ":" + clazz.getName();
			}
			return res;
		}
		
		private Object stringAttrToObject(String attr){
			if (StringUtils.isBlank(attr)) {
				return null;
			}
			String[] split = attr.split("->");
			if (split.length == 1) {
				return split[0];
			}
			Object object = null;
			if (split.length == 2) {
				String json = split[0];
				String mask = split[1];
				if (StringUtils.isBlank(mask)) {
					return json;
				}
				return parseValueMask(json,mask.split(":"),0);
			}
			return object;
		}
		
		private Object parseValueMask(String json,String[] masks,int index) {
			if (index > masks.length || StringUtils.isBlank(json)) {
				return null;
			}
			Class<?> clazz = null;
			boolean isArray = false;
			String name = masks[index];
			try {
				if (name.startsWith("[")) {
					name = name.substring(1);
					isArray = true;
				}
				clazz = Class.forName(name);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (clazz == null) {
				return null;
			}
			if (isArray) {
				List<?> array = JSONArray.parseArray(json, clazz);
				Object[] newInstance = (Object[])Array.newInstance(clazz, array.size());
				if(array.size() > 0){
					++index;
					int i = 0;
					for (Object object : array) {
						newInstance[i] = parseValueMask(object.toString(), masks, index);
						i++;
					}
				}
				return newInstance;
			}
			if(ReflectUtil.isBaseDataType(clazz)){
				try {
					Constructor<?> constructor = clazz.getConstructor(String.class);
					return constructor.newInstance(json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (Collection.class.isAssignableFrom(clazz)) {
				JSONArray array = JSONArray.parseArray(json);
				try {
					@SuppressWarnings("unchecked")
					Collection<Object> newInstance = (Collection<Object>) clazz.newInstance();
					if(array.size() > 0){
						++index;
						for (Object object : array) {
							newInstance.add(parseValueMask(object.toString(), masks, index));
						}
					}
					return newInstance;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (Map.class.isAssignableFrom(clazz)) {
				JSONObject jsonObject = JSON.parseObject(json);
				try {
					@SuppressWarnings("unchecked")
					Map<Object,Object> newInstance = (Map<Object,Object>) clazz.newInstance();
					Set<String> keySet = jsonObject.keySet();
					int kIndex = ++index;
					int vIndex = ++index;
					for (String key : keySet) {
						newInstance.put(parseValueMask(key, masks, kIndex), parseValueMask(jsonObject.get(key)+"", masks, vIndex));
					}
					return newInstance;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				return JSON.parseObject(json, clazz);
			}
			return null;
		}
	}
	
	public static final class JavaSerialization implements Serialization{
		
		public JavaSerialization() {
		}
		
		@Override
		public byte[] serialize(final Object object) throws Exception {
			if(!(object instanceof Serializable))throw new IOException("Need an instance of Serializable !");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			serialize(out, (Serializable) object);
			return out.toByteArray();
		}
		
		private void serialize(final OutputStream out, final Serializable object) throws Exception {
			ObjectOutputStream outputStream = new ObjectOutputStream(out);
			outputStream.writeObject(object);
		}
		
		@Override
		public Object deserialize(byte[] bs) throws Exception {
			return deserialize(new ByteArrayInputStream(bs));
		}
		
		private Object deserialize(final InputStream in) throws Exception {
			ObjectInputStream inputStream = new ObjectInputStream(in);
	        return inputStream.readObject();
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T deserialize(byte[] bs, Class<T> type) throws Exception {
			Object object = deserialize(bs);
			if(object != null && object.getClass().isAssignableFrom(type)){
				return (T) object;
			}
			return null;
		}
	}
}
