package com.tr.eren.tradedataservice.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instrument {

    @JsonProperty("description")
    private String description;
    @JsonProperty("isin")
    private String isin;

}
