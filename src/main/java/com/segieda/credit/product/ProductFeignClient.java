package com.segieda.credit.product;

import com.segieda.credit.model.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "ProductClient", configuration = {ProductFeignClientConfig.class}, url = "${product.url}")
public interface ProductFeignClient {

	@GetMapping(path = {"/product/get"}, produces = {"application/json"})
	List<ProductDto> getProducts();

	@PostMapping(path = {"/product/create"}, produces = {"application/json"})
	void createProduct(ProductDto product);
}