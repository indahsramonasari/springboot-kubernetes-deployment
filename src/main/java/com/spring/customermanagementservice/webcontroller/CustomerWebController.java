package com.spring.customermanagementservice.webcontroller;

import com.spring.customermanagementservice.domain.CustomerRequest;
import com.spring.customermanagementservice.domain.Response;
import com.spring.customermanagementservice.service.CustomerManagementService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/v1")
public class CustomerWebController {

    @Autowired
    private CustomerManagementService customerManagementService;

    @PostMapping(value = "/pendaftarannasabah", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addCustomer(@RequestBody CustomerRequest customerRequest) throws Exception {
        return customerManagementService.addCustomer(customerRequest);
    }

    @PostMapping(value = "/approvalnasabah", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response approvalCustomer(@RequestBody CustomerRequest customerRequest) throws Exception {
        return customerManagementService.approvalCustomer(customerRequest);
    }

    @PostMapping(value = "/rejectnasabah", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response rejectCustomer(@RequestBody CustomerRequest customerRequest) throws Exception {
        return customerManagementService.rejectCustomer(customerRequest);
    }

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloWorld(){
        return "Hello!";
    }


}
