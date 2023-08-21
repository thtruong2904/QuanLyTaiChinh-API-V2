package com.tranhuutruong.finance.build.requests;

import lombok.Data;

import java.sql.Date;

@Data
public class GoalRequest {
    private String name;

    private Long amount;

    private Date deadline;
}
