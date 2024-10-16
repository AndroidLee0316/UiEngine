package com.pasc.business.workspace.impl;

import com.pasc.business.workspace.ConfigIdProvider;

public class ConfigIdProviderImpl implements ConfigIdProvider {

    @Override
    public String getConfigIdHome() {
        return "pasc.smt.homepage";
    }
}
