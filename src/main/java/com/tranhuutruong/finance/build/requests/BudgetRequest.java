package com.tranhuutruong.finance.build.requests;

import lombok.Data;

@Data
public class BudgetRequest {
    private Long amount;

    private String description;
}
