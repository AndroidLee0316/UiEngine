package com.pasc.lib.workspace.handler.impl;

import com.pasc.lib.workspace.handler.HandlerBuilder;
import com.pasc.lib.workspace.handler.SmtProtocolHandler;
import com.pasc.lib.workspace.handler.WebProtocolHandler;

public class HandlerBuilderImpl implements HandlerBuilder {

    @Override
    public WebProtocolHandler getWebProtocolHandler() {
        return new WebProtocolHandlerImpl();
    }

    @Override
    public SmtProtocolHandler getSmtProtocolHandler() {
        return new SmtProtocolHandlerImpl();
    }
}
