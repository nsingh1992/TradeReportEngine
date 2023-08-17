package com.Vanguard.TradeReportEngine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Vanguard.TradeReportEngine.entities.TradeReportEngine;

@Repository
public interface TradeReportEngineRepo extends JpaRepository<TradeReportEngine, Integer> {
	
	@Query("SELECT t FROM TradeReportEngine t WHERE t.seller_party = ?1 and t.premium_currency = ?2")
	List<TradeReportEngine> findDataBySellerAndCurrency(String seller, String currency);

}
