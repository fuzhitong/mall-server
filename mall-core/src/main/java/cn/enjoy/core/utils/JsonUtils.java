package cn.enjoy.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class JsonUtils {
	private static ObjectMapper om = new ObjectMapper();
	static {
		om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	public static ObjectMapper getOm(){
		return om;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(String json) {
		try {
			return om.readValue(json, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> String map2json(Map<String, T> map) {
		return obj2json(map);
	}

	public static String obj2json(Object o){
		try {
			return om.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static  <T> T json2Obj(String json, Class<T> valueType){
		try {
			return om.readValue(json, valueType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//如需转换为list,只需要 List<User> list = json2Obj(value,  new TypeReference<ArrayList<User>>(){});即可
	public static  <T> T json2Obj(String json, TypeReference<T> valueTypeRef){
		try {
			return om.readValue(json, valueTypeRef);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    /**
     * 
     * @创建人 ZhouMin
     * @创建时间 2016年4月12日
     * @创建目的【json转换为ArrayList】
     * @修改目的【修改人：，修改时间：】
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> json2ArrayList(String json, Class<T> clazz) {
        ArrayList<T> list = JsonUtils.json2Obj(json, new TypeReference<ArrayList<T>>() {});
        return list;
    }
	
	public static String getSignData(String json){
		Map<String,String> jsonMap = jsonToMap(json);
		String result = null;
		if(jsonMap != null) {
			Map<String,String> map = new TreeMap<String, String>();
			Set<Map.Entry<String, String>> set = jsonMap.entrySet();
			for (Map.Entry<String, String> entry : set) {
				map.put(entry.getKey(), entry.getValue());
			}
			StringBuilder sb = new StringBuilder();
			for (String key : map.keySet()) {
				sb.append("&").append(key).append("=");
				sb.append(map.get(key));
			}
			result = sb.substring(1);
		}
		return result;
	}


}
