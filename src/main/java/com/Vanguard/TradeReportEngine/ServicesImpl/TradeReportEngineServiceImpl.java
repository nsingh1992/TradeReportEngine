package com.Vanguard.TradeReportEngine.ServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Vanguard.TradeReportEngine.TradeReportEngineRepo;
import com.Vanguard.TradeReportEngine.Services.TradeReportEngineService;
import com.Vanguard.TradeReportEngine.entities.TradeReportEngine;

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
		List<TradeReportEngine> listFromFirstCondition = tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[0], listOfPremiumCurrency[0]);
		List<TradeReportEngine> listFromSecondCondition = tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[1], listOfPremiumCurrency[1]);
		combinedTradeReportEngineList.addAll(listFromFirstCondition);
		combinedTradeReportEngineList.addAll(listFromSecondCondition);
		// Anagram check		
		checkAndRemoveForAnagram(combinedTradeReportEngineList);
		
		return combinedTradeReportEngineList;	
	}

	private void checkAndRemoveForAnagram(CopyOnWriteArrayList<TradeReportEngine> combinedTradeReportEngineList) {
		for(TradeReportEngine tradeReportEngine:combinedTradeReportEngineList) {
			String sellerParty = tradeReportEngine.getSeller_party();
			String buyerParty = tradeReportEngine.getBuyer_party();
			char[] seller = new char[sellerParty.length()];
			char[] buyer = new char[buyerParty.length()];
			sellerParty.getChars(0, sellerParty.length(), seller, 0);
			buyerParty.getChars(0, buyerParty.length(), buyer, 0);
			Arrays.sort(seller);
			Arrays.sort(buyer);
			int count = 0;
			for (int i = 0; i < seller.length; i++) {
				if (seller[i] == buyer[i]) {
					count++;
				}else {
					break;
				}
			}		
			if(count == seller.length) {
				combinedTradeReportEngineList.remove(tradeReportEngine);
			}
		}
	}

}
