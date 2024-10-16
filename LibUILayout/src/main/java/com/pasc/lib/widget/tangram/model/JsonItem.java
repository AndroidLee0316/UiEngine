package com.pasc.lib.widget.tangram.model;

import org.json.JSONObject;

public class JsonItem implements DataSourceItem {

  private JSONObject jsonObject;

  public JSONObject getJsonObject() {
    return jsonObject;
  }

  public void setJsonObject(JSONObject jsonObject) {
    this.jsonObject = jsonObject;
  }

  @Override public Object optValue(String name) {
    if (jsonObject != null) {
      return jsonObject.opt(name);
    }
    return null;
  }
}
