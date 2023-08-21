package com.tranhuutruong.finance.build.requests;

import lombok.Data;

import java.sql.Date;

@Data
public class TransactionRequest {
    private String name;

    private Long amount;

    private String location;

    private Date date;

    private String description;
}
