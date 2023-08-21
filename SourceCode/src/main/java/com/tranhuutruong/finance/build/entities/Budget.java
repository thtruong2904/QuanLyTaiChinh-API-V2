package com.tranhuutruong.finance.build.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@Entity
@Table(name = "budget")
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_category_id")
    private UserCategory userCategory;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "fromdate")
    private Date fromDate;

    @Column(name = "todate")
    private Date toDate;

    @Column(name = "description")
    private String description;
}
