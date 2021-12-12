package com.tr.eren.tradedataservice.api.service;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.mapper.CandlestickDTOMapper;
import com.tr.eren.tradedataservice.api.model.Candlestick;
import com.tr.eren.tradedataservice.common.model.Quote;
import com.tr.eren.tradedataservice.common.dao.QuoteDAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CandlestickServiceImpl implements CandlestickService {

    @Autowired
    private QuoteDAOImpl quoteDAOImpl;

    @Override
    public List<CandlestickDTO> getCandlesticks(String isin) {
        log.info("Processing instrument: " + isin);
        List<CandlestickDTO> candlesticks = new ArrayList<>();
        List<Quote> quotes = new ArrayList<>();
        // Get the time of request. (This could also be fetched from controller for more accuracy but I wanted to separate logic from controller)
        LocalDateTime requestedTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        log.info("Request time is: " + requestedTime);
        quotes.addAll(quoteDAOImpl.getQuotesByIsin(isin, requestedTime));

        // Map grouping quotes by DateTime
        Map<LocalDateTime, List<Quote>> map = quotes.stream().collect(Collectors.groupingBy(Quote::getDateTimeMinutes));

        // Fill in the missing timestamps
        if (!map.isEmpty()) {
            for (int i = 29; i >= 0; i--) {
                LocalDateTime keyTime = requestedTime.minusMinutes(i);
                if (!map.containsKey(keyTime)) {
                    if (map.containsKey(keyTime.minusMinutes(i + 1)))
                        map.put(keyTime, map.get(keyTime.minusMinutes(i + 1)));
                }
            }
        }

        for (Map.Entry<LocalDateTime, List<Quote>> entry : map.entrySet()) {
            LocalDateTime openTimeStamp = entry.getKey();
            // Some stream operations to get desired values (e.g max, min, first)
            BigDecimal openPrice = entry.getValue().stream().findFirst().orElse(null).getPrice();
            BigDecimal highPrice = entry.getValue().stream().max(Comparator.comparing(Quote::getPrice)).orElse(null).getPrice();
            BigDecimal lowPrice = entry.getValue().stream().min(Comparator.comparing(Quote::getPrice)).orElse(null).getPrice();
            BigDecimal closePrice = entry.getValue().stream().reduce((first, second) -> second).orElse(null).getPrice();
            LocalDateTime closeTimestamp = openTimeStamp.plusMinutes(1);
            Candlestick candlestick = new
                    Candlestick(openTimeStamp,
                    openPrice,
                    highPrice,
                    lowPrice,
                    closePrice,
                    closeTimestamp);
            candlesticks.add(CandlestickDTOMapper.mapToDTO(candlestick));
        }

        return candlesticks;
    }
}
