package com.ih2ome.peony.watermeterInterface.yunding;

import com.ih2ome.peony.watermeterInterface.IWatermeter;

public class YunDingWatermeter implements IWatermeter {

    private static final String TOKEN_KEY = "YUN_DING_WATERMETER_TOKEN";
    private static final String UID_KEY = "YUN_DING_WATERMETER_UID";
    private static final String EXPRIES_TIME = "YUN_DING_WATERMETER_EXPRIES_TIME";
    private static final String BASE_URL = "https://dev-lockapi.dding.net:8090/openspi/v1";

    @Override
    public void findHomeState(String home_id) {

    }
}
