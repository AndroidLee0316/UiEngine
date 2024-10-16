package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.text.TextUtils;
import com.pasc.lib.widget.tangram.util.AssetUtils;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultDataManager {
  private static final DefaultDataManager ourInstance = new DefaultDataManager();

  private HashMap<String, JSONObject> dataMap = new HashMap<String, JSONObject>();

  static DefaultDataManager getInstance() {
    return ourInstance;
  }

  private DefaultDataManager() {
  }

  public JSONObject getDefaultData(Context context, String key) {
    JSONObject jsonObject = dataMap.get(key);
    if (jsonObject != null) {
      return jsonObject;
    }
    String assetsJson = AssetUtils.getString(context, "defaultComponentConfig/" + key + ".json");
    try {
      if (!TextUtils.isEmpty(assetsJson)) {
        jsonObject = new JSONObject(assetsJson);
        dataMap.put(key, jsonObject);
        return jsonObject;
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
