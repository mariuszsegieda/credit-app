package com.segieda.credit.service;

import com.segieda.credit.model.CreditDto;

import java.util.Collection;

public interface CreditService {

    Long createCredit(CreditDto creditDto);

    Collection<CreditDto> findAll();
}

