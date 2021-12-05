package com.tr.eren.tradedataservice.api.mapper;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.model.Candlestick;

/*
**
* Mapper class for Candlestick to CandlestickDTO mapping
 */
public class CandlestickDTOMapper {
    public static CandlestickDTO map(Candlestick candlestick) {
        return CandlestickDTO.builder()
                .openTimestamp(candlestick.getOpenTimestamp())
                .openPrice(candlestick.getOpenPrice())
                .highPrice(candlestick.getHighPrice())
                .lowPrice(candlestick.getLowPrice())
                .closePrice(candlestick.getClosePrice())
                .closeTimestamp(candlestick.getCloseTimestamp())
                .build();
    }
}
