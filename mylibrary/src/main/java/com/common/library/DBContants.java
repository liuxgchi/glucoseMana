package com.common.library;

import android.content.Context;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件库访问参数映射
 */
public class DBContants {
	/** 文本数据类型: JSON对象 */ 
	protected static final String ENTITY_TYPE_SINGLE = "1";
	/** 文本数据类型: JSON数组 */ 
	protected static final String ENTITY_TYPE_ARRAY = "2";

    public static final String EXAM_STEP_ONE = "exam_step_one.json";

    public static final String EXAM_STEP_TWO = "exam_step_two.json";

	/** 类与文件名称的映射关系 */ 
	public static Map<Class<?>, String> ENTITY_DB_MAP = new HashMap<Class<?>, String>();
	static {
//		ENTITY_DB_MAP.put(EvaluatedUser.class, "EvaluatedUser.json"); // 测评人信息
//		ENTITY_DB_MAP.put(WelcomPage.class, "WelcomePage.json"); // 欢迎页信息
//        ENTITY_DB_MAP.put(MobileUser.class,"MobileUser.json");
//        ENTITY_DB_MAP.put(ExaminationAnswer.class,"ExaminationAnswer.json");
//        ENTITY_DB_MAP.put(Reporter.class,"Reporter.json");
//        ENTITY_DB_MAP.put(Weather.class,"Weatherx.json");
//
//        ENTITY_DB_MAP.put(SchemalType.class,"SchemalType.json");
//        ENTITY_DB_MAP.put(HealthSaying.class,"HealthSaying.json");
	}
	
	/** 文件存储对象描述 */ 
	public static Map<Class<?>, String> TYPE_DB_MAP = new HashMap<Class<?>, String>();
	static {
		// 单一对象
//		TYPE_DB_MAP.put(EvaluatedUser.class, ENTITY_TYPE_SINGLE); // 测评人信息
//        TYPE_DB_MAP.put(MobileUser.class,ENTITY_TYPE_SINGLE);
//        TYPE_DB_MAP.put(ExaminationAnswer.class,ENTITY_TYPE_SINGLE);
//        TYPE_DB_MAP.put(Reporter.class,ENTITY_TYPE_SINGLE);
//        TYPE_DB_MAP.put(WelcomPage.class, ENTITY_TYPE_SINGLE); // 欢迎页信息
//
//        // 数组对象
//        TYPE_DB_MAP.put(SchemalType.class, ENTITY_TYPE_ARRAY); // 欢迎页信息
//        TYPE_DB_MAP.put(HealthSaying.class, ENTITY_TYPE_ARRAY);
//        TYPE_DB_MAP.put(Weather.class,ENTITY_TYPE_ARRAY);
	}
	
	/**
	 * 判断当前类是否属于文件类
	 * @param clazz
	 * @return
	 */
	public static boolean isDBClazz(Class<?> clazz) {
		if(ENTITY_DB_MAP.containsKey(clazz)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取文件类型
	 * @param clazz
	 * @return
	 */
	public static String getJSONType(Class<?> clazz) {
		if(TYPE_DB_MAP.containsKey(clazz)) {
			return TYPE_DB_MAP.get(clazz);
		} 
		throw new RuntimeException("请配置类映射的文件信息! " + clazz);
	}

    /**
     * 获取数据实体对应的文件
     * @param ctx
     * @param clazz
     * @param filename
     * @param evaluateuuid
     * @return
     */
    protected static File getDBPath(Context ctx,Class<?> clazz, String filename, String... evaluateuuid) {
        if(evaluateuuid.length > 1) {
            throw new RuntimeException("无法处理多个用户数据。");
        }

        String userid = "" /*UserHelper.getUseruuid(ctx)*/;

        String curuuid = userid;
        if(null == filename) {
            filename = ENTITY_DB_MAP.get(clazz);
        }
        if(null != evaluateuuid && evaluateuuid.length > 0) {
            curuuid = evaluateuuid[0];
        }

        // 处理文件路径
        String path = ctx.getFilesDir() + "/"+userid + "/" +curuuid+"/"+ filename;
        System.out.println(path);
        return new File(path);
    }
}
