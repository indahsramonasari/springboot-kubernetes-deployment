package com.spring.customermanagementservice.webcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.customermanagementservice.CustomerManagementServiceApplication;
import com.spring.customermanagementservice.domain.Response;
import com.spring.customermanagementservice.domain.CustomerRequest;
import com.spring.customermanagementservice.domain.constant.StatusConstant;
import com.spring.customermanagementservice.service.CustomerManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerManagementServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerWebControllerTest {

    @Autowired
    CustomerWebController customerWebController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerManagementService customerManagementService;

    @Test
    public void addCustomer() throws Exception {
        CustomerRequest request = CustomerRequest.builder()
                .fullName("Budianto")
                .address("Jalan Cempaka, Sleman, Yogyakarta")
                .nik("3403109877897779")
                .phoneNumber("085767677876")
                .build();

        Response response = Response.builder()
                .responseCode(StatusConstant.RESPONSE_CODE_SUCCESS)
                .responseStatus(StatusConstant.STATUS_SUCCESS)
                .message("Pendaftaran berhasil dilakukan, status nasabah pending menunggu proses aprroval")
                .build();

        Mockito.when(customerManagementService.addCustomer(request)).thenReturn(response);

        this.mockMvc.perform(post("/v1/pendaftarannasabah")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    public void approvalCustomer() throws Exception {
        CustomerRequest request = CustomerRequest.builder()
                .fullName("Budianto")
                .address("Jalan Cempaka, Sleman, Yogyakarta")
                .nik("3403109877897779")
                .phoneNumber("085767677876")
                .build();

        Response response = Response.builder()
                .responseCode(StatusConstant.RESPONSE_CODE_SUCCESS)
                .responseStatus(StatusConstant.STATUS_SUCCESS)
                .message("Proses approval berhasil, rekening nasabah berhasil dibuat")
                .fullName("Budianto")
                .accountNumber("5656252752")
                .balance(new BigDecimal(0))
                .build();

        Mockito.when(customerManagementService.approvalCustomer(request)).thenReturn(response);

        this.mockMvc.perform(post("/v1/approvalnasabah")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    public void rejectCustomer() throws Exception {
        CustomerRequest request = CustomerRequest.builder()
                .fullName("Budianto")
                .address("Jalan Cempaka, Sleman, Yogyakarta")
                .nik("3403109877897779")
                .phoneNumber("085767677876")
                .build();

        Response response = Response.builder()
                .responseCode(StatusConstant.RESPONSE_CODE_SUCCESS)
                .responseStatus(StatusConstant.STATUS_SUCCESS)
                .message("Proses reject berhasil, status nasabah reject")
                .build();

        Mockito.when(customerManagementService.rejectCustomer(request)).thenReturn(response);


        this.mockMvc.perform(post("/v1/rejectnasabah")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)))
                .andExpect(status().isOk());
    }

}
