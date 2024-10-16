package com.pasc.lib.workspace.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 操作Asset相关的工具集合.
 * Created by chenruihan410 on 2018/09/04.
 */
public class AssetUtils {

    /**
     * 获取文件的字符串内容.
     *
     * @param context  上下文.
     * @param fileName 文件名.
     * @return 字符串.
     */
    public static String getString(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
