package com.Vanguard.TradeReportEngine.ServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Vanguard.TradeReportEngine.Repo.TradeReportEngineRepo;
import com.Vanguard.TradeReportEngine.Services.TradeReportEngineService;
import com.Vanguard.TradeReportEngine.Model.TradeReportEngine;

@Service
public class TradeReportEngineServiceImpl implements TradeReportEngineService {
	
	@Autowired
	private TradeReportEngineRepo tradeReportEngineRepo;

	@Override
	public void addXmlDetailsInDb(List<TradeReportEngine> xmlDetails) {
		tradeReportEngineRepo.saveAll(xmlDetails);
		
	}

	@Override
	public CopyOnWriteArrayList<TradeReportEngine> fetchDetailsFromDb(String[] listOfSellerParty,String[] listOfPremiumCurrency) {
		CopyOnWriteArrayList<TradeReportEngine> combinedTradeReportEngineList = new CopyOnWriteArrayList<>();
		// Assumption: The seller party and premium currency should be given in the order in properties file as the requirement suggests.
		combinedTradeReportEngineList.addAll(tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[0], listOfPremiumCurrency[0]));
		combinedTradeReportEngineList.addAll(tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[1], listOfPremiumCurrency[1]));
		// Anagram check		
		checkAndRemoveForAnagram(combinedTradeReportEngineList);
		
		return combinedTradeReportEngineList;	
	}

	private void checkAndRemoveForAnagram(CopyOnWriteArrayList<TradeReportEngine> combinedTradeReportEngineList) {
		for(TradeReportEngine tradeReportEngine:combinedTradeReportEngineList) {
			if(tradeReportEngine.getSeller_party().length()==tradeReportEngine.getBuyer_party().length()) {
				char[] seller = tradeReportEngine.getSeller_party().toCharArray();
				char[] buyer = tradeReportEngine.getBuyer_party().toCharArray();
				Arrays.sort(seller);
				Arrays.sort(buyer);
				if(Arrays.equals(seller, buyer)) {
					combinedTradeReportEngineList.remove(tradeReportEngine);
				}
			}
		}
		
	}

}
