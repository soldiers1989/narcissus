package com.ih2ome.common.vo;

import java.io.Serializable;
import java.util.HashMap;

public class ReturnValueVO implements Serializable {
    private static final long serialVersionUID = 1L;
    public final static int RESULT_SUCCESS = 0;
    public final static int RESULT_FAIL = -1;

    // 错误编码。
    private int errorCode;

    // 错误描述。
    private String errorDescription;

    // 返回数据。
    private HashMap<String, Object> resultHashMap = new HashMap<String, Object>();

    public ReturnValueVO() {
        this.errorCode = 0;
    }

    public HashMap<String, Object> getResultHashMap() {
        return resultHashMap;
    }

    public void setResultHashMap(HashMap<String, Object> resultHashMap) {
        this.resultHashMap = resultHashMap;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 判断返回是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.errorCode == RESULT_SUCCESS;
    }
}
