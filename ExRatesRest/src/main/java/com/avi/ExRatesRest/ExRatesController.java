package com.avi.ExRatesRest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExRatesController {

	@Autowired
	private ExRatesRepo ratesRepo;
	
	@GetMapping(path="/exrates", produces = "application/json")
	@ResponseBody()
	public ExchangeRateDataObject getExRates()
	{
		ExchangeRateDataObject exRateDataObj = new ExchangeRateDataObject();
		List<ExRatesDb> rateList = ratesRepo.findAll();
		
		HashMap<String, String> ratesMap = new HashMap<String, String>();
				
		for(int i = 0;i<rateList.size();i++)
		{
			if(rateList.get(i).getCurr_type().equals("TIME")) exRateDataObj.setTimestamp(Long.valueOf(rateList.get(i).getRate()));
			else if(rateList.get(i).getCurr_type().equals("BASE")) exRateDataObj.setBase(rateList.get(i).getRate());
			else ratesMap.put(rateList.get(i).getCurr_type(), rateList.get(i).getRate());
		}
		exRateDataObj.setRates(ratesMap);
		
		return exRateDataObj;
	}
}
