package com.pasc.business.affair.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 用户相关数据库
 * Created by duyuan797 on 17/10/30.
 */

@Database(name = UserDb.NAME, version = UserDb.VERSION)
public class UserDb {
    public static final String NAME = "UserDatabase";
    public static final int VERSION = 2;
}
