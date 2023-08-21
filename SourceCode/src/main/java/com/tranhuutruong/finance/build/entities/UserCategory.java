package com.tranhuutruong.finance.build.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "user_category")
@NoArgsConstructor
@AllArgsConstructor
public class UserCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserInformation userInformation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_type_id")
    private CategoryType category;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;
}
