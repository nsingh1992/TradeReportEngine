package com.Vanguard.TradeReportEngine.Services;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.Vanguard.TradeReportEngine.entities.TradeReportEngine;

public interface TradeReportEngineService {
	
	public void addXmlDetailsInDb(List<TradeReportEngine> xmlDetails);
	
	public CopyOnWriteArrayList<TradeReportEngine> fetchDetailsFromDb(String[] listOfSellerParty, String[] listOfPremiumCurrency);

}
