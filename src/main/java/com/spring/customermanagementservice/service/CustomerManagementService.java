package com.spring.customermanagementservice.service;

import com.spring.customermanagementservice.domain.CustomerRequest;
import com.spring.customermanagementservice.domain.Response;
import com.spring.customermanagementservice.domain.constant.StatusConstant;
import com.spring.customermanagementservice.model.Customer;
import com.spring.customermanagementservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@Slf4j
public class CustomerManagementService {

    @Autowired
    private CustomerRepository customerRepository;

    // Pendaftaran nasabah
    public Response addCustomer(CustomerRequest request) throws Exception {
        try {
            if(request == null || request.getNik().isEmpty() || request.getPhoneNumber().isEmpty()) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("Pendaftaran gagal, input tidak sesuai")
                        .build();
            }
            Customer customerExisting = customerRepository.findOneByNikAndPhoneNumber(request.getNik(), request.getPhoneNumber());
            if (customerExisting != null) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("Pendaftaran gagal, nik dan nomor telepon telah terdaftar")
                        .build();
            }

            Customer customer = Customer.builder()
                    .fullName(request.getFullName())
                    .address(request.getAddress())
                    .nik(request.getNik())
                    .phoneNumber(request.getPhoneNumber())
                    .status(StatusConstant.PENDING)
                    .build();

            customerRepository.save(customer);

        } catch (Exception ex) {
            log.error("Error when save data {}", ex);
            throw new Exception(ex);
        }

        return Response.builder()
                .responseCode(StatusConstant.RESPONSE_CODE_SUCCESS)
                .responseStatus(StatusConstant.STATUS_SUCCESS)
                .message("Pendaftaran berhasil dilakukan, status nasabah pending menunggu proses aprroval")
                .build();
    }

    // Approval dan update data nasabah
    public Response approvalCustomer(CustomerRequest request) throws Exception {
        String accountNumber = "";
        String fullName = "";
        try {
            if (request.getNik().isEmpty()) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("Input tidak sesuai")
                        .build();
            }

            Customer customer = customerRepository.findOneByNik(request.getNik());
            if (customer == null) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("NIK belum terdaftar, silahkan melakukan pendaftaran nasabah")
                        .build();
            } else if (customer.getStatus().equals(StatusConstant.APPROVED)) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("Status nasabah sudah approve")
                        .build();
            }
            fullName = customer.getFullName();
            //generate random account number
            accountNumber = String.valueOf(generateAccountNumber());

            //update status dan data nasabah
            customer.setStatus(StatusConstant.APPROVED);
            customer.setBalance(BigDecimal.valueOf(0));
            customer.setAccountNumber(accountNumber);
            customerRepository.save(customer);

        } catch (Exception ex) {
            log.error("Error when update data {}", ex);
            throw new Exception(ex);
        }

        return Response.builder()
                .responseCode(StatusConstant.RESPONSE_CODE_SUCCESS)
                .responseStatus(StatusConstant.STATUS_SUCCESS)
                .fullName(fullName)
                .accountNumber(accountNumber)
                .balance(BigDecimal.valueOf(0))
                .message("Proses approval berhasil, nomor rekening nasabah : ".concat(accountNumber))
                .build();
    }

    // Reject dan update data nasabah
    public Response rejectCustomer(CustomerRequest request) throws Exception {
        try {
            if (request.getNik().isEmpty()) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("Input tidak sesuai")
                        .build();
            }
            Customer customer = customerRepository.findOneByNik(request.getNik());
            if (customer == null) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("NIK belum terdaftar, data nasabah tidak ditemukan")
                        .build();
            }

            if (!customer.getStatus().equals("PENDING")) {
                return Response.builder()
                        .responseCode(StatusConstant.RESPONSE_CODE_FAILED)
                        .responseStatus(StatusConstant.STATUS_FAILED)
                        .message("Status nasabah tidak dapat diubah")
                        .build();
            }

            //update status nasabah menjadi REJECT
            customer.setStatus(StatusConstant.REJECT);
            customerRepository.save(customer);
        } catch (Exception ex) {
            log.error("Error when update data {}", ex);
            throw new Exception(ex);
        }

        return Response.builder()
                .responseCode(StatusConstant.RESPONSE_CODE_SUCCESS)
                .responseStatus(StatusConstant.STATUS_SUCCESS)
                .message("Proses reject berhasil, status nasabah reject")
                .build();
    }

    private int generateAccountNumber() {
        Random random = new Random(System.currentTimeMillis());
        return 1000000000 + random.nextInt(2000000000);
    }

}
