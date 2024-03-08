package com.eat.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

public class MyUtil {
	//转换成json
	public static String toJSON(List lst) {
		String sJSON="[";
		int j=0;
		/**
		 * {}
		 * ,{}
		 */
		for (Object object : lst) {
			if(j==0) {
				sJSON=sJSON+"{";
			}else {
				sJSON=sJSON+",{";
			}
			j++;
			Map<String, String> map=(Map<String, String>) object;
			Set<String> keys=map.keySet();
			int i=0;
			for (String key : keys) {
				//sJSON=sJSON+"'"+key+"':'"+map.get(key)+"',";    
				/*   ‘’
				 * , ''
				 * */
				if(i==0) {
					sJSON=sJSON+"'"+key+"':'"+map.get(key)+"'";
				}else {
					sJSON=sJSON+",'"+key+"':'"+map.get(key)+"'";
				}
				i++;
			}
			 sJSON=sJSON+"}";
			 
		}
		 sJSON=sJSON+"]";
		 return sJSON;
	}
	
	// 获取参数
	public static void setParams(FileItem item, Object obj) {
		try {
			String inputName = item.getFieldName();
			String inputValue = item.getString();
			byte[] b=item.get();
			inputValue= new String(b, "UTF-8");
			Field field = obj.getClass().getDeclaredField(inputName);
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			if ((Byte.TYPE == fieldType) || (Byte.class == fieldType)) {
				field.set(obj, Byte.valueOf(inputValue));
			} else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
				field.set(obj, Short.valueOf(inputValue));
			} else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
				field.set(obj, Integer.valueOf(inputValue));
			} else if (String.class == fieldType) {
				field.set(obj, inputValue);
			} else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
				field.set(obj, Long.valueOf(inputValue));
			} else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
				field.set(obj, Float.valueOf(inputValue));
			} else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
				field.set(obj, Double.valueOf(inputValue));
			} else if (Character.TYPE == fieldType) {
				if ((inputValue != null) && (inputValue.length() > 0))
					field.set(obj, Character.valueOf(inputValue.charAt(0)));
			} else if (Date.class == fieldType) {
				SimpleDateFormat sDf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date date1 = sDf.parse(inputValue);
					Date date = new Date(date1.getTime());
					field.set(obj, date);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
