package com.tr.eren.tradedataservice.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    @JsonProperty("price")
    BigDecimal price;
    @JsonProperty("isin")
    String isin;
    @JsonIgnore
    LocalDateTime dateTime;

    public LocalDateTime getDateTimeMinutes() {
        return this.dateTime.truncatedTo(ChronoUnit.MINUTES);
    }
}
