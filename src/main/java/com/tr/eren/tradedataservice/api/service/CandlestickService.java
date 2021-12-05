package com.tr.eren.tradedataservice.api.service;

import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.model.Candlestick;

import java.util.List;

public interface CandlestickService {
    List<CandlestickDTO> getCandlesticks(String isin);
}
