package com.pasc.lib.workspace.handler;

public interface HandlerBuilder {

    WebProtocolHandler getWebProtocolHandler();

    SmtProtocolHandler getSmtProtocolHandler();
}
