package com.common.library.helper;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by think on 2015/3/15.
 */
public class FileHelper {
    /**
     * 从内置文件中读取文件流
     * @param context
     * @param internal
     * @return
     */
    public static InputStream getInputStream(Context context, String internal) {
        try {
            return context.getResources().getAssets().open(internal);
        } catch (IOException e) {
            throw  new RuntimeException("读取内置文件错误", e);
        }
    }
}
