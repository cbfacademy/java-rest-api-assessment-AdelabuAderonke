package com.cbfacademy.apiassessment.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name="portfolios", uniqueConstraints={@UniqueConstraint(columnNames = {"title"})}
)
public class Portfolio {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(name="name",nullable = false)
    private String name;
    @Column(name="userId",nullable = false)
    private long userId;
    @Column(name="description",nullable = false)
    private String description;
    @Column(name="createdAt",nullable = false)
    private Date createdAt;


}
