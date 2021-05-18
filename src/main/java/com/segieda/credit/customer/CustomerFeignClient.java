package com.segieda.credit.customer;

import com.segieda.credit.model.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@FeignClient(name = "CustomerClient", configuration = {CustomerFeignClientConfig.class}, url = "${customer.url}")
public interface CustomerFeignClient {

	@GetMapping(path = {"/customer/get"}, produces = {"application/json"})
	List<CustomerDto> getCustomers();

	@PostMapping(path = {"/customer/create"}, produces = {"application/json"})
    void createCustomer(CustomerDto customer);
}