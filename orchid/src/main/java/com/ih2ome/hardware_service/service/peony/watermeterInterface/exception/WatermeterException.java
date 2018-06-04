package com.ih2ome.hardware_service.service.peony.watermeterInterface.exception;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class WatermeterException extends Exception {
    public WatermeterException() {
    }

    public WatermeterException(String message) {
        super(message);
    }

    public WatermeterException(String message, Throwable cause) {
        super(message, cause);
    }

    public WatermeterException(Throwable cause) {
        super(cause);
    }

    public WatermeterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
