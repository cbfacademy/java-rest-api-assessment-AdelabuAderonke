package com.cbfacademy.apiassessment.repository;

import com.cbfacademy.apiassessment.model.Investment;
import com.cbfacademy.apiassessment.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment,Long> {
    List<Investment> findByPortfolioId(long id);

    @Query("SELECT investment FROM Investment investment WHERE " +
            "investment.name LIKE CONCAT('%',:query, '%')" +
            "Or investment.symbol LIKE CONCAT('%', :query, '%')")
    List<Investment> searchInvestments(String query);
}
