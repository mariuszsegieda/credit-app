package com.segieda.credit.rest;

import com.segieda.credit.model.CreditDto;
import com.segieda.credit.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "/credit")
@Slf4j
@RequiredArgsConstructor
public class CreditRest {

    private final CreditService creditService;

    @GetMapping(path = "/credit")
    public Collection<CreditDto> getCredit() {
        return creditService.findAll();
    }

    @PostMapping(path = "/createCredit")
    public Long createCredit(@Valid @RequestBody CreditDto creditDto) {
        Long creditId = creditService.createCredit(creditDto);
        return creditId;
    }
}
