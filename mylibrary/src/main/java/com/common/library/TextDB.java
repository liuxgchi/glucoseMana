package com.common.library;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.library.helper.FileHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：模拟文本存储空间
 */
public class TextDB {
    /**
     * 保存对象到配置文件
     * @param ctx
     * @param obj
     * @param evaluateuuid
     */
    public static void save(Context ctx,Object obj,String... evaluateuuid) {
        if(obj instanceof List) { // 判断是否是一个List集合
            List<?> list = (List<?>)obj;
            if(list.size() == 0) { // 空数据不进行任何操作
                throw new RuntimeException("传入参数没有映射到任何目标类！");
            }

            if(DBContants.isDBClazz(list.get(0).getClass())) { // 判断是否一个数据实体对象
                updateList(ctx,list, list.get(0).getClass(), null, evaluateuuid); // List数据映射更新至文件
            } else{
                throw new RuntimeException("当前的类没有映射文件 ：" +list.get(0).getClass());
            }
        } else if (DBContants.isDBClazz(obj.getClass())){ // 判断是否一个数据实体对象
            updateOne(ctx,obj, obj.getClass(), null, evaluateuuid); // 将对象写入映射更新至文件
        } else {
            throw new RuntimeException("当前的类没有映射文件 ：" + obj.getClass());
        }
    }

    /**
     * 名保存对象到指定文件
     * @param ctx
     * @param filename
     * @param obj
     * @param evaluateuuid
     */
    public static void save(Context ctx, String filename, Object obj, String... evaluateuuid) {
        if(obj instanceof List) { // 判断是否是一个List集合
            List<?> list = (List<?>)obj;
            if(list.size() == 0) { // 空数据不进行任何操作
                throw new RuntimeException("传入参数没有映射到任何目标类！");
            }

            if(DBContants.isDBClazz(list.get(0).getClass())) { // 判断是否一个数据实体对象
                updateList(ctx,list, list.get(0).getClass(), filename, evaluateuuid); // List数据映射更新至文件
            } else{
                throw new RuntimeException("当前的类没有映射文件 ：" +list.get(0).getClass());
            }
        } else if (DBContants.isDBClazz(obj.getClass())){ // 判断是否一个数据实体对象
            updateOne(ctx,obj, obj.getClass(), filename, evaluateuuid); // 将对象写入映射更新至文件
        } else if(null != filename || !"".equals(filename)) {
            updateOne(ctx, obj, obj.getClass(), filename, evaluateuuid);
        } else {
            throw new RuntimeException("当前的类没有映射文件 ：" + obj.getClass());
        }
    }

    /**
     * 添加一条数据到指定文件
     * @param ctx
     * @param filename
     * @param e
     * @param evaluateuuid
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void append(Context ctx, String filename, String key, Object e, String... evaluateuuid) {
        // 读取旧文件
        File file = DBContants.getDBPath(ctx, null, filename, evaluateuuid);

        // 读取存储主键
        String keyword = "";
        try {
            Class<?> cls = e.getClass();
            keyword = (String)cls.getMethod(Converter.getMethodName(key, "get")).invoke(e);
        } catch (Exception e1) {
            throw new RuntimeException("获取主键数据错误，请确认是否具有属性【" + key + "】");
        }

        // 文件不存在，保存数据
        String text = getText(file);
        if(!file.exists() || null == text || "".equals(text.trim())) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(keyword, e);

            save(ctx, filename, map, evaluateuuid);
            return;
        }

        // 将新对象拼接上
        HashMap map = readObject(ctx, filename, HashMap.class, evaluateuuid);
        map.put(keyword, e);

        // 将文件写回
        save(ctx, filename, map, evaluateuuid);
    }

    /**
     * 删除指定文件
     * @param ctx
     * @param filename
     * @param evaluateuuid
     */
    public static void remove(Context ctx, String filename, String... evaluateuuid) {
        // 读取旧文件
        File file = DBContants.getDBPath(ctx, null, filename, evaluateuuid);
        // 如果文件存在，那么删除文件
        if(file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除指定的数据对象
     * @param ctx
     * @param filename
     * @param value
     * @param evaluateuuid
     */
    @SuppressWarnings("rawtypes")
    public static void remove(Context ctx, String filename, String value, String... evaluateuuid) {
        // 读取旧文件
        File file = DBContants.getDBPath(ctx, null, filename, evaluateuuid);

        // 文件不存在，保存数据
        String text = getText(file);
        if(null == text || "".equals(text.trim())) {
            throw new RuntimeException("要删除的对象不存在");
        }

        // 将新对象拼接上
        HashMap map = readObject(ctx, filename, HashMap.class, evaluateuuid);
        map.remove(value);

        // 将文件写回
        save(ctx, filename, map, evaluateuuid);
    }

    /**
     * 读取配置文件内容
     * @param ctx
     * @param clazz
     * @param evaluateuuid
     * @return
     */
    public static String readText(Context ctx, Class<?> clazz,String... evaluateuuid) {
        // 读取文件
        File file = DBContants.getDBPath(ctx,clazz,null,evaluateuuid);

        if(!file.exists()) {
            return null;
        }
        return read(file);
    }

    /**
     * 读取配置对象
     * @param ctx
     * @param clazz
     * @param evaluateuuid
     * @return
     */
    public static <T>T readObject(Context ctx, Class<T> clazz, String... evaluateuuid) {
        // 判断是否符合标准
        String type = DBContants.getJSONType(clazz);
        if(!DBContants.ENTITY_TYPE_SINGLE.equals(type)) {
            throw new RuntimeException(clazz + "映射类型为JSONArray，请使用readList(Class<T> clazz)进行获取");
        }

        // 读取文件
        File file = DBContants.getDBPath(ctx,clazz,null,evaluateuuid);

        // 解析成对应的结构
        String text = read(file);
        return JSONObject.parseObject(text, clazz);
    }

    /**
     * 读取指定文件对象
     * @param ctx
     * @param filename
     * @param clazz
     * @param evaluateuuid
     * @return
     */
    public static <T>T readObject(Context ctx, String filename, Class<T> clazz, String...evaluateuuid) {
        // 读取文件
        File file = DBContants.getDBPath(ctx, clazz, filename, evaluateuuid);

        // 解析成对应的结构
        String text = read(file);
        return JSONObject.parseObject(text, clazz);
    }

    /**
     * 读取配置对象列表
     * @param ctx
     * @param filename
     * @param clazz
     * @param evaluateuuid
     * @return
     */
    public static <T>List<T> readList(Context ctx, String filename, Class<T> clazz, String...evaluateuuid) {
        // 读取文件
        File file = DBContants.getDBPath(ctx, clazz, filename, evaluateuuid);

        // 解析成对应的结构
        String text = read(file);
        if(text == null || "".equals(text.trim())) {
            return null;
        }
        return JSONArray.parseArray(text, clazz);
    }

    /**
     * 读取多人测评列表专用： 读取某个测评人UUID后10条
     * @param ctx
     * @param filename
     * @param clazz
     * @param currentuuid
     * @param size
     * @param evaluateuuid
     * @return
     */
    @SuppressWarnings({"rawtypes" })
    public static <T>List<T> readPage(Context ctx,String filename, Class<T> clazz, String currentuuid, int size, String... evaluateuuid) {
        // 读取数据文件
        HashMap map = readObject(ctx, filename, HashMap.class, evaluateuuid);

        // 将MAP转成list
        List<T> list = Converter.getListFromMap(map, clazz);

        // 对列表进行排序
        Converter.sortEvaluateUserList(list);

        // 计算起止位置
        List<T> result = Converter.getList(list, currentuuid, size);

        // 返回结果
        return result;
    }

    /**
     * 读取配置对象列表
     * @param ctx
     * @param clazz
     * @param evaluateuuid
     * @return
     */
    public static <T>List<T> readList(Context ctx,Class<T> clazz,String... evaluateuuid) {
        // 判断是否符合标准
        String type = DBContants.getJSONType(clazz);
        if(!DBContants.ENTITY_TYPE_ARRAY.equals(type)) {
            throw new RuntimeException(clazz + "映射类型为JSON对象，请使用readObject(Class<T> clazz)进行获取");
        }

        // 读取文件
        File file = DBContants.getDBPath(ctx,clazz,null,evaluateuuid);

        // 解析成对应的结果
        String text = read(file);
        return JSONArray.parseArray(text, clazz);
    }

    /**
     * 读取指定文件的文本
     * @param is
     * @return
     */
    public static String getText(InputStream is) {
        return read(is);
    }

    /**
     * 读取指定文件的文本
     * @param file
     * @return
     */
    public static String getText(File file) {
        try {
            return getText(new FileInputStream(file));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取内置文件内容
     * @param ctx
     * @param filename
     * @return
     */
    public static String getInternalText(Context ctx, String filename) {
        InputStream is = FileHelper.getInputStream(ctx, filename);
        String response = TextDB.getText(is);
        return response;
    }

    /**
     * 指定文件读取数据
     * @param is
     * @param clazz
     * @return
     */
    public static <T>T getObject(InputStream is, Class<T> clazz) {
        // 读取文件
        String text = read(is);
        return JSONObject.parseObject(text, clazz);
    }

    /**
     * 读取列表
     * @param is
     * @param clazz
     * @return
     */
    public static <T>List<T> getList(InputStream is, Class<T> clazz) {
        // 解析成对应的结果
        String text = read(is);
        return JSONArray.parseArray(text, clazz);
    }

    /**
     * 更新集合文件
     * @param ctx
     * @param list
     * @param clazz
     * @param filename
     * @param evaluateuuid
     */
    private static void updateList(Context ctx,List<?> list, Class<?> clazz, String filename, String... evaluateuuid) {
        // 获取文件对象
        File file = DBContants.getDBPath(ctx,clazz,filename,evaluateuuid);
        // 写入JSON数组
        write(file, JSONArray.toJSONString(list));
    }

    /**
     * 更新单个文件
     * @param ctx
     * @param obj
     * @param clazz
     * @param filename
     * @param evaluateuuid
     */
    private static void updateOne(Context ctx,Object obj, Class<?> clazz, String filename, String... evaluateuuid) {
        // 获取文件对象
        File file = DBContants.getDBPath(ctx,clazz,filename,evaluateuuid);
        // 写入JSON对象
        write(file, JSON.toJSONString(obj));
    }

    /**
     * 写入文件
     * @param file
     * @param text
     */
    private static void write(File file, String text) {
        try {
            enable(file);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            output.write(text);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件
     * @param file
     * @return
     */
    private static String read(File file) {
        // 文件不存在，返回空值
        if(!file.exists()) {
            return null;
        }

        // 读取并返回文件
        try {
            String result = new String(); // 完整数据
            String txt = new String(); // 文件中数据

            BufferedReader input = new BufferedReader(new FileReader(file));
            while ((txt = input.readLine()) != null) {
                result += txt;
            }
            input.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("读取文件错误! " + file.getName());
        }
    }

    /**
     * 读取文件
     * @param is
     * @return
     */
    private static String read(InputStream is) {
        // 读取并返回文件
        try {
            String result = new String(); // 完整数据
            String txt = new String(); // 文件中数据

            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            while ((txt = input.readLine()) != null) {
                result += txt;
            }
            input.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("读取文件错误! ");
        }
    }

    /**
     * 建立文件
     * @param file
     * @throws java.io.IOException
     */
    private static void enable(File file) throws IOException {
        if (!file.exists()) {
            String parentPath = file.getParent();
            if (parentPath != null) {
                new File(parentPath).mkdirs();
            }
            file.createNewFile();
        }
    }
}
