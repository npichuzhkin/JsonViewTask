package com.npichuzhkin.JsonViewTask.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.npichuzhkin.JsonViewTask.views.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonView(Views.DetailedUserInformation.class)
    private UUID id;

    @JsonView(Views.DetailedUserInformation.class)
    private String productName;

    @JsonView(Views.DetailedUserInformation.class)
    private BigDecimal amount;
}
