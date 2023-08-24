package com.Vanguard.TradeReportEngine.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="tradereports")
public class TradeReportEngine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int tradeReportId;
	
	private String fileName;
	private String buyer_party;
	private String seller_party;
	private double premium_amount;
	private String premium_currency;
	
	
	public int getTradeReportId() {
		return tradeReportId;
	}
	public void setTradeReportId(int tradeReportId) {
		this.tradeReportId = tradeReportId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBuyer_party() {
		return buyer_party;
	}
	public void setBuyer_party(String buyer_party) {
		this.buyer_party = buyer_party;
	}
	public String getSeller_party() {
		return seller_party;
	}
	public void setSeller_party(String seller_party) {
		this.seller_party = seller_party;
	}
	public double getPremium_amount() {
		return premium_amount;
	}
	public void setPremium_amount(double d) {
		this.premium_amount = d;
	}
	public String getPremium_currency() {
		return premium_currency;
	}
	public void setPremium_currency(String premium_currency) {
		this.premium_currency = premium_currency;
	}
	public TradeReportEngine(int tradeReportId, String fileName, String buyer_party, String seller_party,
			Double premium_amount, String premium_currency) {
		super();
		this.tradeReportId = tradeReportId;
		this.fileName = fileName;
		this.buyer_party = buyer_party;
		this.seller_party = seller_party;
		this.premium_amount = premium_amount;
		this.premium_currency = premium_currency;
	}
	public TradeReportEngine() {
	}
	
	
	
	

}
