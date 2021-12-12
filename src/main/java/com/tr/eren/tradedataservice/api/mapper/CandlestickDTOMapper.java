package com.tr.eren.tradedataservice.api.mapper;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.model.Candlestick;

/*
**
* Mapper class for Candlestick to CandlestickDTO mapping
 */
public class CandlestickDTOMapper {
    public static CandlestickDTO mapToDTO(Candlestick candlestick) {
        return CandlestickDTO.builder()
                .openTimestamp(candlestick.getOpenTimestamp())
                .openPrice(candlestick.getOpenPrice())
                .highPrice(candlestick.getHighPrice())
                .lowPrice(candlestick.getLowPrice())
                .closePrice(candlestick.getClosePrice())
                .closeTimestamp(candlestick.getCloseTimestamp())
                .build();
    }

    public static Candlestick mapToEntity(CandlestickDTO candlestickDTO) {
        //TODO: Implement mapping to entity
        return new Candlestick();
    }
}
