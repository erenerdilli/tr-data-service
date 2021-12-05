package com.tr.eren.tradedataservice.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InstrumentEvent {
    public enum Type {
        ADD,
        DELETE
    }

    @JsonProperty("data")
    private Instrument data;
    @JsonProperty("type")
    private Type type;
}
