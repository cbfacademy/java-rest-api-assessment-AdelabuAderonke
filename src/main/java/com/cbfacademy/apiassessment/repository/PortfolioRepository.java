package com.cbfacademy.apiassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbfacademy.apiassessment.entity.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long>{
}
