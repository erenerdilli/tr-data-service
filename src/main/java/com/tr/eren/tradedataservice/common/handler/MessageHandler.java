package com.tr.eren.tradedataservice.common.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tr.eren.tradedataservice.common.model.InstrumentEvent;
import com.tr.eren.tradedataservice.common.model.Quote;
import com.tr.eren.tradedataservice.common.model.QuoteUpdate;
import com.tr.eren.tradedataservice.common.dao.InstrumentDAO;
import com.tr.eren.tradedataservice.common.dao.QuoteDAOImpl;
import com.tr.eren.tradedataservice.common.mapper.JsonToPojoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.InvalidAttributeValueException;
import java.time.LocalDateTime;
import java.util.*;
/*
**
* Handler class for messages fetched from websocket
 */
@Slf4j
@Component
public class MessageHandler {

    @Autowired
    private QuoteDAOImpl quoteDAOImpl;

    @Autowired
    private InstrumentDAO instrumentDAO;

    public void handleInstrumentMessage(String message) throws InvalidAttributeValueException {
        InstrumentEvent instrumentEvent = new InstrumentEvent();
        try {
            instrumentEvent = JsonToPojoMapper.mapInstrumentEvent(message);
        } catch (JsonProcessingException e) {
            log.error("Unable to process json: " + message);
            e.printStackTrace();
        }
        if (instrumentEvent != null) {
            switch (instrumentEvent.getType()) {
                case ADD:
                    instrumentDAO.saveInstrument(instrumentEvent.getData());
                    break;
                case DELETE:
                    instrumentDAO.removeInstrumentByIsin(instrumentEvent.getData().getIsin());
                    break;
                default:
                    log.error("Invalid attribute value: " + instrumentEvent.getType());
                    throw new InvalidAttributeValueException("Invalid value: " + instrumentEvent.getType());
            }
        }
    }

    public QuoteUpdate handleQuoteMessage(String message) throws InvalidAttributeValueException {
        QuoteUpdate quoteUpdate = new QuoteUpdate();
        try {
            quoteUpdate = JsonToPojoMapper.mapQuteUpdate(message);
        } catch (JsonProcessingException e) {
            log.error("Unable to process json: " + message);
            e.printStackTrace();
            return quoteUpdate;
        }

        if (quoteUpdate != null) {
            Quote quoteToBeAdded = quoteUpdate.getData();
            quoteToBeAdded.setDateTime(LocalDateTime.now());

            String isin = quoteToBeAdded.getIsin();

            if (quoteDAOImpl.getQuoteMap().containsKey(isin)) {
                List<Quote> updatedList = new ArrayList<>(quoteDAOImpl.getQuoteMap().get(isin));
                updatedList.add(quoteToBeAdded);
                quoteDAOImpl.getQuoteMap().put(isin, updatedList);
            } else {
                quoteDAOImpl.getQuoteMap().put(isin, Arrays.asList(quoteToBeAdded));
            }
        }
        return quoteUpdate;
    }

}
