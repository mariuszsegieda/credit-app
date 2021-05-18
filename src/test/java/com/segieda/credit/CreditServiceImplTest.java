package com.segieda.credit;

import com.segieda.credit.customer.CustomerFeignClient;
import com.segieda.credit.model.Credit;
import com.segieda.credit.model.CreditDto;
import com.segieda.credit.model.CustomerDto;
import com.segieda.credit.model.ProductDto;
import com.segieda.credit.product.ProductFeignClient;
import com.segieda.credit.repo.CreditRepository;
import com.segieda.credit.service.CreditMapper;
import com.segieda.credit.service.CreditServiceImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CreditServiceImplTest {
    @InjectMocks
    CreditServiceImpl creditService;
    @Mock
    CustomerFeignClient customerFeignClient;
    @Mock
    ProductFeignClient productFeignClient;
    @Mock
    CreditMapper creditMapper;
    @Mock
    CreditRepository creditRepository;

    @Test
    void shouldCheckExecution(){
        // given
        CreditDto creditDto = new CreditDto();
        Credit credit = new Credit();
        credit.setId(1L);
        Mockito.when(creditRepository.save(Mockito.any())).thenReturn(credit);
        // when
        Long result = creditService.createCredit(creditDto);
        // then
        then(creditRepository).should().save(credit);
        then(productFeignClient).should().createProduct(any(ProductDto.class));
        then(customerFeignClient).should().createCustomer(any(CustomerDto.class));
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(result).isEqualTo(1);
        });
    }

    @Test
    void shouldCheckWhenCreditIsNull(){
        try {
            // when
            creditService.createCredit(null);
        } catch (Exception e) {
            // then
            then(creditRepository).should(never()).save(any(Credit.class));
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            assertThat(e.getMessage()).isEqualTo("Credit shouldn't be null.");
        }
    }
}
