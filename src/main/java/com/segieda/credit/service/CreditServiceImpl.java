package com.segieda.credit.service;

import com.segieda.credit.customer.CustomerFeignClient;
import com.segieda.credit.model.Credit;
import com.segieda.credit.model.CreditDto;
import com.segieda.credit.product.ProductFeignClient;
import com.segieda.credit.repo.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final CustomerFeignClient customerFeignClient;
    private final ProductFeignClient productFeignClient;
    private final CreditMapper creditMapper;


    @Override
    public Collection<CreditDto> findAll() {
        Map<Long, CreditDto> creditMap = creditRepository.findAll().stream()
                .map(creditMapper::mapToDto)
                .collect(Collectors.toMap(CreditDto::getId, credit -> credit));

        updateCreditsCustomers(creditMap);

        updateCreditsProducts(creditMap);

        return creditMap.values();
    }

    private void updateCreditsProducts(Map<Long, CreditDto> creditMap) {
        productFeignClient.getProducts()
                .forEach(product -> Optional.ofNullable(creditMap.get(product.getCreditId()))
                        .ifPresent(credit -> credit.setProduct(product)));
    }

    private void updateCreditsCustomers(Map<Long, CreditDto> creditMap) {
        customerFeignClient.getCustomers()
                .forEach(customer -> Optional.ofNullable(creditMap.get(customer.getCreditId()))
                        .ifPresent(credit -> credit.setCustomer(customer)));
    }

    @Override
    public Long createCredit(CreditDto creditDto) {
        if (creditDto == null) {
            throw new IllegalArgumentException("Credit shouldn't be null.");
        }
//        map to credit entity and save in DB
        Credit credit = creditMapper.mapToEntity(creditDto);
        Credit savedCredit = creditRepository.save(credit);
        creditDto.setId(savedCredit.getId());

//        filled product and customer with credit id, may be here or in CreditDto. I've make in CreditDto
//        creditDto.getProduct().setCreditId(savedCredit.getId());
//        creditDto.getCustomer().setCreditId(savedCredit.getId());

//        execute product endpoint to save product
        productFeignClient.createProduct(creditDto.getProduct());
//        execute customer endpoint to save customer
        customerFeignClient.createCustomer(creditDto.getCustomer());

        return creditDto.getId();
    }
}
