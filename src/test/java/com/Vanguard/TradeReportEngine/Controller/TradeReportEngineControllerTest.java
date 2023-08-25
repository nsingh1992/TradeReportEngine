package com.Vanguard.TradeReportEngine.Controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.Vanguard.TradeReportEngine.Repo.TradeReportEngineRepo;
import com.Vanguard.TradeReportEngine.Services.TradeReportEngineService;
import com.Vanguard.TradeReportEngine.ServicesImpl.TradeReportEngineServiceImpl;
import com.Vanguard.TradeReportEngine.Exception.ResourceNotFoundException;
import com.Vanguard.TradeReportEngine.Model.TradeReportEngine;

//@ExtendWith(MockitoExtension.class)

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
