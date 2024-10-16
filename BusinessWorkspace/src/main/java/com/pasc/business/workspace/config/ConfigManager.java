package com.pasc.business.workspace.config;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import ikidou.reflect.TypeBuilder;

/**
 * Created by chenruihan410 on 2018/08/24.
 */
public class ConfigManager {

    private ConfigManager() {
    }

    private static ConfigManager configManager = new ConfigManager();
    private Gson gson = new Gson();

    /**
     * 对象转Json字符串
     *
     * @param o
     * @return
     */
    public static String configModelToJson(Object o) {
        if (o == null) return "";
        return configManager.gson.toJson(o);
    }

    /**
     * json字符串转对象
     *
     * @param jsonString
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> T configJsonToModel(String jsonString, Class<T> modelClass) {
        if (jsonString == null) return null;
        try {
            return configManager.gson.fromJson(jsonString, modelClass);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 集合对象转json字符串
     *
     * @param modelList
     * @return
     */
    public static String configModelsToJson(List modelList) {
        if (modelList == null) return "";
        return configManager.gson.toJson(modelList);
    }

    /**
     * json字符串转集合对象
     *
     * @param jsonString
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> List<T> configJsonToModels(String jsonString, Class<T> modelClass) {
        try {
            Type listType = TypeBuilder
                    .newInstance(List.class)
                    .addTypeParam(modelClass)
                    .build();
            List<T> modelList = configManager.gson.fromJson(jsonString, listType);
            return modelList;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }

    }
}
