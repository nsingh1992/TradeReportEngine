package com.Vanguard.TradeReportEngine.Repo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Vanguard.TradeReportEngine.TradeReportEngineApplication;
import com.Vanguard.TradeReportEngine.Model.TradeReportEngine;

import jakarta.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
@Transactional
@SpringBootTest(classes = TradeReportEngineApplication.class)
public class TradeReportEngineRepoTest {

	@Autowired
	private TradeReportEngineRepo tradeReportEngineRepo;

	@Test
	public void saveTradeReportEngineTest() {
		TradeReportEngine tradeReportEngine = getTradeReportEngine();
		tradeReportEngineRepo.save(tradeReportEngine);
		Assertions.assertTrue(tradeReportEngine.getTradeReportId() > 0);
	}

	@Test
	public void findAllTradeReportEngineTest() {
		TradeReportEngine tradeReportEngine = getTradeReportEngine();
		tradeReportEngineRepo.save(tradeReportEngine);
		List<TradeReportEngine> result = new ArrayList<>();
		tradeReportEngineRepo.findAll().forEach(e -> result.add(e));
		assertEquals(result.size(), 1);
	}

	@Test
	public void findByIdTradeReportEngineTest() {
		TradeReportEngine tradeReportEngine = getTradeReportEngine();
		tradeReportEngineRepo.save(tradeReportEngine);
		TradeReportEngine result = tradeReportEngineRepo.findById(tradeReportEngine.getTradeReportId()).get();
		assertEquals(tradeReportEngine.getTradeReportId(), result.getTradeReportId());
	}

	@Test
	public void deleteByIdTradeReportEngineTest() {
		TradeReportEngine tradeReportEngine = getTradeReportEngine();
		tradeReportEngineRepo.save(tradeReportEngine);
		tradeReportEngineRepo.deleteById(tradeReportEngine.getTradeReportId());
		List<TradeReportEngine> result = new ArrayList<>();
		tradeReportEngineRepo.findAll().forEach(e -> result.add(e));
		assertEquals(result.size(), 0);
	}

	private TradeReportEngine getTradeReportEngine() {
		TradeReportEngine tradeReportEngine = new TradeReportEngine();
		tradeReportEngine.setBuyer_party("EMU_BANK");
		tradeReportEngine.setFileName("event2.xml");
		tradeReportEngine.setSeller_party("LEFT_BANK");
		tradeReportEngine.setPremium_amount(400.0);
		tradeReportEngine.setPremium_currency("AUD");
		return tradeReportEngine;
	}

}
