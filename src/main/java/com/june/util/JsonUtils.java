package com.june.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.june.vo.Response;

public final class JsonUtils {

    private JsonUtils() {
        throw new UnsupportedOperationException("No instance for this class!");
    }

    public static void main(String[] args) {

        int num = 100;
        Map<Object, Object> map = new HashMap<>(num);
        for (int i = 0; i < num; i++) {
            List<Response> list = new ArrayList<>(10);
            Response response = new Response(i, i + "");
            for (int j = 0; j < 10; j++) {
                // users.add(new
                // User().setEmail("email"+j).setId(j).setPassword("password"+j).setUserName("user"+j));
                response.put(j + "", new Response(j, j + ""));
                list.add(response);
            }
            map.put(i, list);
        }

        long startTime = System.nanoTime();
        long endTime = System.nanoTime();

        System.out.println("================ JsonUtils.obeject2json ===================");
        startTime = System.currentTimeMillis();
        System.out.println(object2json(map));
        // object2json(map);
        // System.out.println(object2json(response));
        endTime = System.currentTimeMillis();
        System.out.println("cost " + (endTime - startTime) + "ms");
        System.out.println();

        System.out.println("================ JSONObject.valueToString =================");
        startTime = System.currentTimeMillis();
        System.out.println(JSONObject.valueToString(map));
        // JSONObject.valueToString(map);
        // System.out.println(JSONObject.valueToString(response));
        endTime = System.currentTimeMillis();
        System.out.println("cost " + (endTime - startTime) + "ms");
        System.out.println();

        System.out.println("================ JSON.toJSONString ========================");
        startTime = System.currentTimeMillis();
        System.out.println(JSON.toJSONString(map));
        // JSON.toJSONString(map);
        // System.out.println(JSON.toJSONString(response));
        endTime = System.currentTimeMillis();
        System.out.println("cost " + (endTime - startTime) + "ms");
    }

    public static String object2json(Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return string2json(obj.toString());
        }
        if (obj instanceof CharSequence) {
            return "\"" + string2json(obj.toString()) + "\"";
        }
        if (obj instanceof Object[]) {
            return array2json((Object[]) obj);
        }
        if (obj instanceof Collection) {
            return collection2json((Collection<?>) obj);
        }
        if (obj instanceof Map) {
            return map2json((Map<?, ?>) obj);
        }
        return bean2json(obj);
    }

    /**
     * 将普通JavaBean对象转为JSON字符串 (此方法性能太差，不建议使用。建议使用如org.json、fastjson等工具库代替)
     * @param bean 待转换的JavaBean对象
     * @return
     */
    public static String bean2json(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if (props != null) {
            try {
                for (int i = 0; i < props.length; i++) {
                    String name = object2json(props[i].getName());
                    String value = object2json(props[i].getReadMethod().invoke(bean));
                    json.append(name).append(":").append(value).append(",");
                }
                json.setCharAt(json.length() - 1, '}');
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static String collection2json(Collection<?> collection) {
        StringBuilder json = new StringBuilder("[");
        if (collection != null && collection.size() > 0) {
            for (Object obj : collection) {
                json.append(object2json(obj)).append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder("[");
        if (array != null && array.length > 0) {
            for (int i = 0, len = array.length; i < len; i++) {
                json.append(object2json(array[i])).append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    public static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder("{");
        if (map != null && map.size() > 0) {
            for (Entry<?, ?> entry : map.entrySet()) {
                json.append(object2json(entry.getKey())).append(":").append(object2json(entry.getValue())).append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }

    public static String string2json(String s) {
        if (s == null) {
            return "null";
        }
        if (s.length() == 0) {
            return "\"\"";
        }
        StringBuilder json = new StringBuilder();
        for (int i = 0, len = s.length(); i < len; i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                case '\\':
                    json.append("\\");
                    json.append(ch);
                    break;
                case '\b':
                    json.append("\\b");
                    break;
                case '\f':
                    json.append("\\f");
                    break;
                case '\n':
                    json.append("\\n");
                    break;
                case '\r':
                    json.append("\\r");
                    break;
                case '\t':
                    json.append("\\t");
                    break;
                case '/':
                    json.append("\\/");
                    break;
                default:
                    if (ch >= '\u0000' && ch <= '\u001F') {
                        String ss = Integer.toHexString(ch);
                        json.append("\\u");
                        for (int k = 0; k < 4 - ss.length(); k++) {
                            json.append('0');
                        }
                        json.append(ss.toUpperCase());
                    } else {
                        json.append(ch);
                    }
            }
        }
        return json.toString();
    }
}
