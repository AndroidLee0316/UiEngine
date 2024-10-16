package com.pasc.business.life.router.location;

import com.pasc.lib.router.BaseJumper;

/**
 * Created by ex-huangzhiyi001 on 18/10/22.
 */
public class LocationJumper extends BaseJumper {
    public static final String PATH_LOCATION_SERVICE = "/map/interfaceService/service";

    public static ILocation getILocation() {
        // TODO
        return getService(PATH_LOCATION_SERVICE);
    }
}
