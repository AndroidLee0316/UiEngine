package com.pasc.lib.widget.tangram;

public class MarqueeTextModel {

    private String id;
    private String title;
    private String skipUrl;
    private boolean closable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
    }
}
