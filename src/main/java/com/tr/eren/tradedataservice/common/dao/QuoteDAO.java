package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.api.model.Quote;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuoteDAO {
    public static Map<String, List<Quote>> quoteMap = new HashMap<>();

    public List<Quote> getQuotesByIsin(String isin) {
        return quoteMap.get(isin);
    }
}
