package com.tranhuutruong.finance.build.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import java.sql.Date;


import java.util.List;


@Data
@Builder
@Entity
@Table(name = "goal")
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "status")
    private String status;

    @Column(name = "deadline")
    private Date deadline;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "goal")
    @JsonManagedReference
    private List<GoalProgress> goalDetailList;

    public Long getTotalDeposit()
    {
        Long total = 0L;
        if(goalDetailList != null)
        {
            for(GoalProgress g: this.getGoalDetailList())
            {
                total += g.getDeposit();
            }
        }
        return total;
    }
}
