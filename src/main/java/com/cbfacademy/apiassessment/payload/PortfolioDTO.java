package com.cbfacademy.apiassessment.payload;

import java.util.Date;

import lombok.Data;
@Data
public class PortfolioDTO {
    private long id;
    private String name;
    private long userId;
    private String description;
    private Date createdAt;

}
