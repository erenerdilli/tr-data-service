package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.api.model.Instrument;
import lombok.extern.slf4j.Slf4j;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class InstrumentDAO {
    /*
    * Datasource for Instruments
     */
    public static List<Instrument> instrumentList = new ArrayList<>();


    public static Instrument saveInstrument(Instrument instrument) throws InvalidAttributeValueException {
        if (instrument != null) {
            instrumentList.add(instrument);
            log.info("Created new instrument: " + instrument.getIsin());
        }
        return instrument;
    }

    public static void removeInstrumentByIsin(String isin) {
        for (int i = 0; i < instrumentList.size(); i++) {
            if (instrumentList.get(i).getIsin().equals(isin)) {
                instrumentList.remove(i);
                log.info("Removed instrument: " + isin);
                break;
            }
        }
        // Call the function to remove quotes with given isin
        QuoteDAO.removeQuotesByIsin(isin);
    }


}
