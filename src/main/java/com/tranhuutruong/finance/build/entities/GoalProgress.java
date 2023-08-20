package com.tranhuutruong.finance.build.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@Entity
@Table(name = "goal_progress")
@NoArgsConstructor
@AllArgsConstructor
public class GoalProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goal_id")
    @JsonBackReference
    private Goal goal;

    @Column(name = "deposit")
    private Long deposit;

    @Column(name = "date_add_deposit")
    private Date date;
}
