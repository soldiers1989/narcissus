package com.ih2ome.peony.smartlockInterface.factory;

import com.ih2ome.peony.smartlockInterface.ISmartLock;
import com.ih2ome.sunflower.vo.thirdVo.smartLock.enums.SmartLockFirm;
import com.ih2ome.peony.smartlockInterface.exception.SmartLockException;
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

    public static ISmartLock createSmartLock(String  id) throws SmartLockException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String [] ids = id.split("_");
        if(ids.length<2){
            throw new SmartLockException("id格式不规范");
        }
        String type = ids[0];
        SmartLockFirm smartLockFirm = SmartLockFirm.getByCode(type);
        ISmartLock iSmartLock = null;
        iSmartLock = (ISmartLock) Class.forName(smartLockFirm.getClazz()).newInstance();
        return iSmartLock;
    }
}
