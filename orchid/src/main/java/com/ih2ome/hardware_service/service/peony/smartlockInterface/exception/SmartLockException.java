package com.ih2ome.hardware_service.service.peony.smartlockInterface.exception;

/**
 * @author Sky
 * @create 2017/12/25
 * @email sky.li@ixiaoshuidi.com
 **/
public class SmartLockException extends Exception{
    public SmartLockException() {
    }

    public SmartLockException(String message) {
        super(message);
    }

    public SmartLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmartLockException(Throwable cause) {
        super(cause);
    }

    public SmartLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
