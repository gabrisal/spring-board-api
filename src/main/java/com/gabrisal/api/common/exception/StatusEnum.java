package com.gabrisal.api.common.exception;

public enum StatusEnum {

    SUCCESS(200, ""),
    BAD_REQUEST(400, "BAD_REQUEST"),
    SERVER_ERROR(500, "SERVER_ERROR")
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
