package com.pasc.business.mine.callback;

/*
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/7/26
 * @des
 * @modify
 */
public interface OnResultListener<Data> {

    void onSuccess(Data data);

    void onFailed(String code, String msg);
}
