package com.spring.customermanagementservice.domain.constant;

public class StatusConstant {

    private StatusConstant() {
        throw new IllegalStateException("StatusConstant class");
    }

    public static final String RESPONSE_CODE_SUCCESS = "00";
    public static final String RESPONSE_CODE_FAILED = "01";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";
    public static final String PENDING = "PENDING";
    public static final String APPROVED = "APPROVED";
    public static final String REJECT = "REJECT";

}
