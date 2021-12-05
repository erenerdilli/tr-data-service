package com.tr.eren.tradedataservice.api.controller;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.exception.InstrumentNotFoundException;
import com.tr.eren.tradedataservice.api.exception.NoQuotesFoundException;
import com.tr.eren.tradedataservice.api.model.Candlestick;
import com.tr.eren.tradedataservice.api.model.Instrument;
import com.tr.eren.tradedataservice.api.model.Quote;
import com.tr.eren.tradedataservice.api.service.CandlestickService;
import com.tr.eren.tradedataservice.common.dao.InstrumentDAO;
import com.tr.eren.tradedataservice.common.dao.QuoteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/candlestickapi")
public class CandleStickController {

    @Autowired
    private CandlestickService candlestickService;

    @GetMapping("/candlesticks/quotes")
    public List<Quote> retreiveAllQuotes() {
        return Arrays.asList(new Quote());
    }

    @GetMapping("/candlesticks/{isin}")
    public ResponseEntity<List<CandlestickDTO>> retreiveQuotesByIsin(@PathVariable String isin) {
        Instrument instrument = InstrumentDAO.instrumentList.stream().filter(i -> i.getIsin().equals(isin)).findFirst().orElse(null);
        if (instrument == null) {
            throw new InstrumentNotFoundException("isin: " + isin);
        }
        if (!QuoteDAO.quoteMap.containsKey(isin)) {
            throw new NoQuotesFoundException("isin: " + isin);
        }
        return ResponseEntity.ok(candlestickService.getCandlesticks(isin));
    }

}
