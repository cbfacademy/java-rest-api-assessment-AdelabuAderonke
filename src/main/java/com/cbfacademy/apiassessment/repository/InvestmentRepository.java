package com.cbfacademy.apiassessment.repository;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment,Long> {
}
