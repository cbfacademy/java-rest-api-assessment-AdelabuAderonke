package com.cbfacademy.apiassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbfacademy.apiassessment.model.Portfolio;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long>{
    //boolean existsByName(String portfolioName);
}
