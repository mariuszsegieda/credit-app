package com.segieda.credit.service;

import com.segieda.credit.model.Credit;
import com.segieda.credit.model.CreditDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreditMapperTest {

    CreditMapper creditMapper = Mappers.getMapper(CreditMapper.class);

    @Test
    void mapToEntity() {
        // given
        CreditDto creditDto = CreditDto.builder()
                .creditName("example")
                .build();
        // when
        Credit credit = creditMapper.mapToEntity(creditDto);
        // then
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(credit.getCreditName()).isEqualTo(creditDto.getCreditName());
        });
    }

    @Test
    void mapToDto() {
        // given
        Credit credit = new Credit();
        credit.setId(1L);
        credit.setCreditName("example");
        // when
        CreditDto creditDto = creditMapper.mapToDto(credit);
        // then
        SoftAssertions.assertSoftly(s -> {
            s.assertThat(creditDto.getId()).isEqualTo(credit.getId());
            s.assertThat(creditDto.getCreditName()).isEqualTo(credit.getCreditName());
        });
    }
}