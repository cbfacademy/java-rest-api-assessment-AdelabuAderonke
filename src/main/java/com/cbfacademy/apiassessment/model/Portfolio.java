package com.cbfacademy.apiassessment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.AUTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String portfolioName;
    @Column(name = "userId", unique = true, nullable = false)
    private long userId;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @Column(name = "createdAt")
    private Date createdAt;
    @OneToMany(mappedBy = "portfolio",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Investment> investments = new HashSet<>();

}
