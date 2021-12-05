package com.tr.eren.tradedataservice.common.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tr.eren.tradedataservice.api.model.InstrumentEvent;
import com.tr.eren.tradedataservice.api.model.Quote;
import com.tr.eren.tradedataservice.api.model.QuoteUpdate;
import com.tr.eren.tradedataservice.common.dao.InstrumentDAO;
import com.tr.eren.tradedataservice.common.dao.QuoteDAO;
import com.tr.eren.tradedataservice.common.mapper.JsonToPojoMapper;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.*;

public class MessageHandler {

    public static void handleInstrumentMessage(String message) throws InvalidAttributeValueException {
        InstrumentEvent instrumentEvent = new InstrumentEvent();
        try {
            instrumentEvent = JsonToPojoMapper.mapInstrumentEvent(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (instrumentEvent != null) {
            switch (instrumentEvent.getType()) {
                case ADD:
                    InstrumentDAO.saveInstrument(instrumentEvent.getData());
                    break;
                case DELETE:
                    InstrumentDAO.removeInstrumentByIsin(instrumentEvent.getData().getIsin());
                    break;
                default:
                    throw new InvalidAttributeValueException("Invalid value: " + instrumentEvent.getType());
            }
        }
    }

    public static void handleQuoteMessage(String message) throws InvalidAttributeValueException {
        QuoteUpdate quoteUpdate = new QuoteUpdate();
        try {
            quoteUpdate = JsonToPojoMapper.mapQuteUpdate(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (quoteUpdate != null) {
            Quote quoteToBeAdded = quoteUpdate.getData();
            quoteToBeAdded.setDateTime(LocalDateTime.now());

            String isin = quoteToBeAdded.getIsin();

            if (QuoteDAO.quoteMap.containsKey(isin)) {
                List<Quote> updatedList = new ArrayList<>(QuoteDAO.quoteMap.get(isin));
                updatedList.add(quoteToBeAdded);
                QuoteDAO.quoteMap.put(isin, updatedList);
            } else {
                QuoteDAO.quoteMap.put(isin, Arrays.asList(quoteToBeAdded));
            }
        }

    }

}
