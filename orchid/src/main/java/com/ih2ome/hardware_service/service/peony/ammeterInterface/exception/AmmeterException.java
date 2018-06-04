package com.ih2ome.hardware_service.service.peony.ammeterInterface.exception;

/**
 * <br>
 *
 * @author Lucius
 * create by 2017/11/27
 * @Emial Lucius.li@ixiaoshuidi.com
 */
public class AmmeterException extends Exception {
    public AmmeterException() {
    }

    public AmmeterException(String message) {
        super(message);
    }

    public AmmeterException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmmeterException(Throwable cause) {
        super(cause);
    }

    public AmmeterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
