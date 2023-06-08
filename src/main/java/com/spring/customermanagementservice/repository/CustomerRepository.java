package com.spring.customermanagementservice.repository;

import com.spring.customermanagementservice.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "SELECT * FROM customer WHERE nik=?1", nativeQuery = true)
    Customer findOneByNik(String nik);

    @Query(value = "SELECT * FROM customer WHERE nik=?1 AND phone_number=?2", nativeQuery = true)
    Customer findOneByNikAndPhoneNumber(String nik, String phoneNumber);

    @Query(value = "SELECT * FROM customer WHERE account_number=?1", nativeQuery = true)
    Customer findOneByAccountNumber(String accountNumber);

}
