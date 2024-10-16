package com.pasc.lib.widget.tangram.util;

import com.tmall.wireless.tangram.dataparser.concrete.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardUtils {

    public static JSONObject getHeaderTemplate(Card card) {
        if (card == null) {
            return null;
        }
        JSONObject cardHeader = card.optJsonObjectParam("headerTemplate");
        if (cardHeader != null) {
            try {
                return new JSONObject(cardHeader.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取卡片上的项模板.
     *
     * @param card 卡片.
     * @param type 模板类型.
     * @return 项模板.
     */
    public static JSONObject getItemTemplate(Card card, String type) {
        if (card == null || type == null) {
            return null;
        }
        JSONArray itemTemplates = card.optJsonArrayParam("itemTemplates");
        if (itemTemplates != null) {
            int length = itemTemplates.length();
            for (int i = 0; i < length; i++) {
                try {
                    JSONObject jsonObject = itemTemplates.getJSONObject(i);
                    String cellType = jsonObject.getString("type");
                    if (cellType != null && cellType.equals(type)) {
                        return new JSONObject(jsonObject.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
