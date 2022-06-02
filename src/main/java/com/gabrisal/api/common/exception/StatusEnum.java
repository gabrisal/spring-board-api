package com.gabrisal.api.common.exception;

public enum StatusEnum {

    SUCCESS(200, ""),

    BAD_REQUEST(400, "BAD_REQUEST"),
    SERVER_ERROR(500, "SERVER_ERROR"),

    INVALID_EMPTY_DATA(501, "INVALID_EMPTY_DATA"),
    INVALID_SIZE_DATA(502, "INVALID_SIZE_DATA"),
    INVALID_DUPLICATED_DATA(503, "INVALID_DUPLICATED_DATA"),
    INVALID_COUNT_DATA(504, "INVALID_COUNT_DATA")
    ;

    int statusCode;
    String statusValue;

    StatusEnum(int statusCode, String statusValue) {
        this.statusCode = statusCode;
        this.statusValue = statusValue;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusValue() {
        return statusValue;
    }
}
