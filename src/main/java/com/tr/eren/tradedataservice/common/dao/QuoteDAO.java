package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.common.model.Quote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface QuoteDAO {
    List<Quote> getQuotesByIsin(String isin, LocalDateTime requestedTime);

    void removeQuotesByIsin(String isin);

    Map<String, List<Quote>> getQuoteMap();
}
