package com.tr.eren.tradedataservice.common.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.eren.tradedataservice.api.model.InstrumentEvent;
import com.tr.eren.tradedataservice.api.model.QuoteUpdate;
/*
**
* Mapper class for mapping json strings to POJO
 */
public class JsonToPojoMapper {

    public static InstrumentEvent mapInstrumentEvent(String jsonObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        InstrumentEvent instrumentEvent = mapper.readValue(jsonObject, InstrumentEvent.class);
        return instrumentEvent;
    }

    public static QuoteUpdate mapQuteUpdate(String jsonObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        QuoteUpdate quoteUpdate = mapper.readValue(jsonObject, QuoteUpdate.class);
        return quoteUpdate;
    }
}
