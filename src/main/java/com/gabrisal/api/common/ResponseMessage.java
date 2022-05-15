package com.gabrisal.api.common;

import lombok.Data;

@Data
public class ResponseMessage {

    private int errCode;
    private String errMsg;
    private Object data;

    public ResponseMessage() {
        this.errCode = StatusEnum.BAD_REQUEST.getStatusCode();
        this.errMsg = StatusEnum.BAD_REQUEST.getStatusValue();
        this.data = null;
    }
}
