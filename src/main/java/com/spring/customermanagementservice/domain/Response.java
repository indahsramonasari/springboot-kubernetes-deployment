package com.spring.customermanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response implements Serializable {

    private static final long serialVersionUID = -2024878730735688197L;

    private String  responseCode;
    private String  responseStatus;
    private String  message;
    private String fullName;
    private String accountNumber;
    private BigDecimal balance;

}