package com.tr.eren.tradedataservice.api.service;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.mapper.CandlestickDTOMapper;
import com.tr.eren.tradedataservice.api.model.Candlestick;
import com.tr.eren.tradedataservice.api.model.Quote;
import com.tr.eren.tradedataservice.common.dao.QuoteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CandlestickServiceImpl implements CandlestickService {

    @Autowired
    private QuoteDAO quoteDAO;

    @Override
    public List<CandlestickDTO> getCandlesticks(String isin) {
        List<CandlestickDTO> candlesticks = new ArrayList<>();
        List<Quote> quotes = new ArrayList<>();
        quotes.addAll(quoteDAO.getQuotesByIsin(isin));

        Map<LocalDateTime, List<Quote>> map = quotes.stream().collect(Collectors.groupingBy(Quote::getDateTimeMinutes));
        for (Map.Entry<LocalDateTime, List<Quote>> entry : map.entrySet()) {
            Candlestick candlestick = new
                    Candlestick(entry.getKey(),
                    entry.getValue().stream().findFirst().orElse(null).getPrice(),
                    entry.getValue().stream().max(Comparator.comparing(Quote::getPrice)).orElse(null).getPrice(),
                    entry.getValue().stream().min(Comparator.comparing(Quote::getPrice)).orElse(null).getPrice(),
                    entry.getValue().stream().reduce((first, second) -> second).orElse(null).getPrice(),
                    entry.getValue().stream().findFirst().orElse(null).getDateTimeMinutes().plusMinutes(1));
            candlesticks.add(CandlestickDTOMapper.map(candlestick));
        }
        return candlesticks;
    }
}
