package com.pasc.business.mine.manager;



import com.pasc.business.mine.bean.JsAddressJson;
import com.pasc.business.mine.callback.UserAddressChangeListener;
import com.pasc.business.mine.params.AddressItem;
import com.pasc.business.mine.resp.AddressIdResp;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/8/24
 * @des
 * @modify
 */
public class UserAddressManager {
    private UserAddressManager() {
    }

    public static UserAddressManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类,只有在装载该内部类时才会去创建单例对象
     */
    private static class SingletonHolder {
        private static final UserAddressManager INSTANCE = new UserAddressManager();
    }

    private List<UserAddressChangeListener> userAddressChangeListeners = new ArrayList<>();

    public void addUserAddressChangeListener(UserAddressChangeListener listener) {
        userAddressChangeListeners.add(listener);
    }

    public void removeUserAddressChangeListener(UserAddressChangeListener listener) {
        userAddressChangeListeners.remove(listener);
    }

    public void notifyUserAddressChangeForAdd(JsAddressJson addressItem) {
        for (UserAddressChangeListener listener : userAddressChangeListeners) {
            listener.addAddress(addressItem);
        }
    }

    public void notifyUserAddressChangeForModify() {
        for (UserAddressChangeListener listener : userAddressChangeListeners) {
            listener.updateAddress();
        }
    }

    public void notifyUserAddressChangeForDelete() {
        for (UserAddressChangeListener listener : userAddressChangeListeners) {
            listener.deleteAddress();
        }
    }

}
