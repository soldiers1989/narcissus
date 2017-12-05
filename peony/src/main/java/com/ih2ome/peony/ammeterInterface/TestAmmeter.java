package com.ih2ome.peony.ammeterInterface;

import com.ih2ome.peony.ammeterInterface.exception.AmmeterException;
import com.ih2ome.peony.ammeterInterface.powerBee.util.PowerBeeAmmeterUtil;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/12/5
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class TestAmmeter {
    public static void main(String [] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, AmmeterException {
//        IAmmeter iAmmeter = (IAmmeter) Class.forName(AMMETER_FIRM.POWER_BEE.getClazz()).newInstance();
//        iAmmeter.getAmmeterInfo("59d5014acb72df7111ba379c");
        PowerBeeAmmeterUtil.getToken();
    }
}
