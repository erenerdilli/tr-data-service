package com.tr.eren.tradedataservice.api.controller;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.exception.InstrumentNotFoundException;
import com.tr.eren.tradedataservice.api.exception.NoQuotesFoundException;
import com.tr.eren.tradedataservice.common.model.Instrument;
import com.tr.eren.tradedataservice.common.model.Quote;
import com.tr.eren.tradedataservice.api.service.CandlestickService;
import com.tr.eren.tradedataservice.common.dao.InstrumentDAOImpl;
import com.tr.eren.tradedataservice.common.dao.QuoteDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/candlestickapi")
public class CandleStickController {

    @Autowired
    private CandlestickService candlestickService;

    @Autowired
    private InstrumentDAOImpl instrumentDAOImpl;

    @GetMapping("/candlesticks/quotes")
    public List<Quote> retreiveAllQuotes() {
        return Arrays.asList(new Quote());
    }

    @GetMapping("/candlesticks")
    public ResponseEntity<List<CandlestickDTO>> retreiveCandlesticksByIsin(@RequestParam String isin) {
        Instrument instrument = instrumentDAOImpl.getInstrumentList().stream().filter(i -> i.getIsin().equals(isin)).findFirst().orElse(null);
        if (instrument == null) {
            throw new InstrumentNotFoundException("isin: " + isin);
        }
        if (!QuoteDAOImpl.quoteMap.containsKey(isin)) {
            throw new NoQuotesFoundException("isin: " + isin);
        }
        return ResponseEntity.ok(candlestickService.getCandlesticks(isin));
    }

}
