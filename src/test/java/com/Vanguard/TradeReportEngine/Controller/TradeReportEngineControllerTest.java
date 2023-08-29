package com.Vanguard.TradeReportEngine.Controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.Vanguard.TradeReportEngine.Model.TradeReportEngine;
import com.Vanguard.TradeReportEngine.Services.TradeReportEngineService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TradeReportEngineController.class)
public class TradeReportEngineControllerTest {

	 @Value("${listOfSellerParty}")
	 private String[] listOfSellerParty;

	 @Value("${listOfPremiumCurrency}")
	 private String[] listOfPremiumCurrency;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TradeReportEngineService tradeReportEngineService;

	@Test
	public void testMultipleFilesUploadRestController_uploadFilesPositive() throws Exception {
		CopyOnWriteArrayList<TradeReportEngine> dbDetails = new CopyOnWriteArrayList<>();
		TradeReportEngine tradeReportEngine1 = new TradeReportEngine();
		  tradeReportEngine1.setTradeReportId(1);
		  tradeReportEngine1.setBuyer_party("EMU_BANK");
		  tradeReportEngine1.setSeller_party("LEFT_BANK");
		  tradeReportEngine1.setFileName("event0.xml");
		  tradeReportEngine1.setPremium_currency("AUD");
		  tradeReportEngine1.setPremium_amount(100.0);
		  dbDetails.add(tradeReportEngine1);
		when(tradeReportEngineService.fetchDetailsFromDb(listOfSellerParty, listOfPremiumCurrency)).thenReturn(dbDetails);


		MockMultipartFile files = new MockMultipartFile("files", "event0.xml", "text/xml",
				this.getClass().getResourceAsStream("/event0.xml"));

		mockMvc.perform(multipart("/tradeReportEngine/upload").file(files)).andExpect(MockMvcResultMatchers.status().isOk());

	}

}