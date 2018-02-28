package com.ih2ome.peony.smartlockInterface.factory;

import com.ih2ome.common.utils.StringUtils;
import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirmEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <br>
 *
 * @author Lucius
 * create by 2018/1/15
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class SmartLockOperateFactory {

    static{
        Logger Log = LoggerFactory.getLogger(SmartLockOperateFactory.class);
        Log.info("门锁工厂启动");
    }

    public static ISmartLock createSmartLock(String  firm) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(StringUtils.isEmpty(firm)){
            throw new SmartLockException("id格式不规范");

        }
        SmartLockFirmEnum smartLockFirm = SmartLockFirmEnum.getByCode(firm);
        ISmartLock iSmartLock = null;
        iSmartLock = (ISmartLock) Class.forName(smartLockFirm.getClazz()).newInstance();
        return iSmartLock;
    }
}
