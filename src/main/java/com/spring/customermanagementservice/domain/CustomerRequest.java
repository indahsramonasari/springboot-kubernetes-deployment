package com.spring.customermanagementservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest implements Serializable {

    private static final long serialVersionUID = -4332690234267952954L;

    private String fullName;
    private String address;
    private String nik;
    private String phoneNumber;

}
