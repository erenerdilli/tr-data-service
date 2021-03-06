package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.api.model.Quote;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class QuoteDAO {
    /*
     * Datasource for Quotes
     */
    public static Map<String, List<Quote>> quoteMap = new HashMap<>();

    public List<Quote> getQuotesByIsin(String isin, LocalDateTime requestedTime) {
        return quoteMap.get(isin).stream().filter(x -> x.getDateTimeMinutes().isBefore(requestedTime) && x.getDateTimeMinutes().isAfter(requestedTime.minusMinutes(30))).collect(Collectors.toList());
    }

    public static void removeQuotesByIsin(String isin) {
        //TODO: Implement quote removal from datasource
        quoteMap.remove(isin);
    }
}
