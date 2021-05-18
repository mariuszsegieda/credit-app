package com.segieda.credit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDto {

    private Long id;
    // example validation, annotation @Valid is in CreditRest - @PostMapping
    @NotNull
    @Size(min = 3, message = "wrong creditName, you must correct")
    private String creditName;
    private CustomerDto customer;
    private ProductDto product;
    // filled product and customer with credit id
    public void setId(Long id) {
        this.id = id;
        Optional.ofNullable(customer).ifPresent(c -> c.setCreditId(id));
        Optional.ofNullable(product).ifPresent(p -> p.setCreditId(id));
    }
}
