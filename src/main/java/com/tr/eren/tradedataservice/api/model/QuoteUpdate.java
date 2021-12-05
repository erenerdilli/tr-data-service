package com.tr.eren.tradedataservice.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuoteUpdate {
    public enum Type {
        QUOTE
    }

    @JsonProperty("data")
    private Quote data;
    @JsonProperty("type")
    private QuoteUpdate.Type type;
}
