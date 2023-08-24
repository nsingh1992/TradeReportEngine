package com.Vanguard.TradeReportEngine.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Vanguard.TradeReportEngine.Constants.Constant;
import com.Vanguard.TradeReportEngine.Exception.ResourceNotFoundException;
import com.Vanguard.TradeReportEngine.Services.TradeReportEngineService;
import com.Vanguard.TradeReportEngine.Model.TradeReportEngine;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@RestController
@RequestMapping("/tradeReportEngine")
public class TradeReportEngineController {

	@Autowired
	private TradeReportEngineService tradeReportEngineService;
	
	 @Value("${listOfSellerParty}")
	 private String[] listOfSellerParty;
	 
	 @Value("${listOfPremiumCurrency}")
	 private String[] listOfPremiumCurrency;
	 
	 @Value("${buyerPartyReferenceExpression}")
	 private String buyerPartyReferenceExpression;
	 
	 @Value("${sellerPartyReferenceExpression}")
	 private String sellerPartyReferenceExpression;
	 
	 @Value("${paymentAmountCurrencyExpression}")
	 private String paymentAmountCurrencyExpression;
	 
	 @Value("${paymentAmountExpression}")
	 private String paymentAmountExpression;

	@PostMapping("/upload")
	public ResponseEntity<CopyOnWriteArrayList<TradeReportEngine>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		List<TradeReportEngine> xmlDataList = new ArrayList<>();
		for (MultipartFile file : files) {
			xmlDataList.add(fetchDataFromXml(file));
		}
		tradeReportEngineService.addXmlDetailsInDb(xmlDataList);
		CopyOnWriteArrayList<TradeReportEngine> dbDetails = tradeReportEngineService.fetchDetailsFromDb(listOfSellerParty,listOfPremiumCurrency);		
		if(dbDetails == null)
			throw new ResourceNotFoundException("The given data not found in db");
		
		return ResponseEntity.ok(dbDetails);
	}

	private TradeReportEngine fetchDataFromXml(MultipartFile inputFile) {
		List<String> xmlListForExpression = new ArrayList<String>();
		TradeReportEngine tradeReportEngineObject = new TradeReportEngine();
		tradeReportEngineObject.setFileName(inputFile.getOriginalFilename());
		
		try {
			InputStream inputFileStream = inputFile.getInputStream();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFileStream);
			doc.getDocumentElement().normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();

			xmlListForExpression.add(buyerPartyReferenceExpression);
			xmlListForExpression.add(sellerPartyReferenceExpression);
			xmlListForExpression.add(paymentAmountCurrencyExpression);
			xmlListForExpression.add(paymentAmountExpression);

			for (String xmlItem : xmlListForExpression) {
				

				NodeList nodeList = (NodeList) xPath.compile(xmlItem).evaluate(doc, XPathConstants.NODESET);

				for (int i = 0; i < nodeList.getLength(); i++) {
					Node nNode = nodeList.item(i);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						if (xmlItem.contains(Constant.BUYER_PARTY_REFERENCE)) {
							tradeReportEngineObject.setBuyer_party(eElement.getAttribute(Constant.HREF));
						}else if (xmlItem.contains(Constant.SELLER_PARTY_REFERENCE)) {
							tradeReportEngineObject.setSeller_party(eElement.getAttribute(Constant.HREF));
						}else if (xmlItem.contains(Constant.CURRENCY)) {
							tradeReportEngineObject.setPremium_currency(eElement.getTextContent());
						}else if (xmlItem.contains(Constant.AMOUNT)) {
							tradeReportEngineObject.setPremium_amount(Double.parseDouble(eElement.getTextContent()));
						}
					}
				}

			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return tradeReportEngineObject;
	}

}
