package com.tr.eren.tradedataservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
**
* Class for mapping our model to dto
*
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandlestickDTO {
    private LocalDateTime openTimestamp;
    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closePrice;
    private LocalDateTime closeTimestamp;
}
