package com.Vanguard.TradeReportEngine.ServicesImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Vanguard.TradeReportEngine.Repo.TradeReportEngineRepo;
import com.Vanguard.TradeReportEngine.Model.TradeReportEngine;


@ExtendWith(MockitoExtension.class)
public class TradeReportEngineServiceImplTest {
	
	  @Mock
	  private TradeReportEngineRepo tradeReportEngineRepo;
	  
	  @InjectMocks
	  private TradeReportEngineServiceImpl tradeReportEngineServiceImpl;
	  
	  String[] listOfSellerParty = {"EMU_BANK","BISON_BANK"};
	  String[] listOfBuyerParty = {"EMU_BANK","LEFT_BANK"};
	  List<TradeReportEngine> listFromFirstCondition = new ArrayList<>();
	  List<TradeReportEngine> listFromSecondCondition = new ArrayList<>();

	  @BeforeEach
	  public void setUp() {
		  TradeReportEngine tradeReportEngine1 = new TradeReportEngine();
		  tradeReportEngine1.setTradeReportId(1);
		  tradeReportEngine1.setBuyer_party("EMU_BANK");
		  tradeReportEngine1.setSeller_party("BISON_BANK");
		  tradeReportEngine1.setFileName("event0.xml");
		  tradeReportEngine1.setPremium_currency("USD");
		  tradeReportEngine1.setPremium_amount(100.0);
		  listFromFirstCondition.add(tradeReportEngine1);
		  	  
		  TradeReportEngine tradeReportEngine2 = new TradeReportEngine();
		  tradeReportEngine2.setTradeReportId(2);
		  tradeReportEngine2.setBuyer_party("LEFT_BANK");
		  tradeReportEngine2.setSeller_party("EMU_BANK");
		  tradeReportEngine2.setFileName("event0.xml");
		  tradeReportEngine2.setPremium_currency("AUD");
		  tradeReportEngine2.setPremium_amount(300.0);
		  listFromSecondCondition.add(tradeReportEngine2);
		  
	  }

	  @Test
	  void affirmativeFetchDetailsFromDb() throws IOException {
		  when(tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[0], listOfBuyerParty[0])).thenReturn(listFromFirstCondition);
		  when(tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[1], listOfBuyerParty[1])).thenReturn(listFromSecondCondition);
		  	  
		  CopyOnWriteArrayList<TradeReportEngine> result = tradeReportEngineServiceImpl.fetchDetailsFromDb(listOfSellerParty, listOfBuyerParty);
		  
		  assertThat(result.get(0).getSeller_party()).isEqualTo("BISON_BANK");
	  
	  }
	  
	  @Test
	  void affirmativeFetchDeatilsFromDb_WithAnagramParties() throws IOException {
		  TradeReportEngine tradeReportEngine3 = new TradeReportEngine();
		  tradeReportEngine3.setTradeReportId(2);
		  tradeReportEngine3.setBuyer_party("EMU_BANK");
		  tradeReportEngine3.setSeller_party("EMU_BANK");
		  tradeReportEngine3.setFileName("event0.xml");
		  tradeReportEngine3.setPremium_currency("AUD");
		  tradeReportEngine3.setPremium_amount(300.0);
		  listFromSecondCondition.add(tradeReportEngine3);
		  
		  when(tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[0], listOfBuyerParty[0])).thenReturn(listFromFirstCondition);
		  when(tradeReportEngineRepo.findDataBySellerAndCurrency(listOfSellerParty[1], listOfBuyerParty[1])).thenReturn(listFromSecondCondition);
  
		  CopyOnWriteArrayList<TradeReportEngine> result = tradeReportEngineServiceImpl.fetchDetailsFromDb(listOfSellerParty, listOfBuyerParty);
		  
		  assertThat(result.size()).isEqualTo(2);
	  
	  }

}
