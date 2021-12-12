package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.common.model.Instrument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InstrumentDAOImpl implements InstrumentDAO {
    /*
    * Datasource for Instruments
     */
    private static List<Instrument> instrumentList = new ArrayList<>();

    @Autowired
    private QuoteDAO quoteDAO;


    @Override
    public Instrument saveInstrument(Instrument instrument) throws InvalidAttributeValueException {
        if (instrument != null) {
            instrumentList.add(instrument);
            log.info("Created new instrument: " + instrument.getIsin());
        }
        return instrument;
    }

    public void removeInstrumentByIsin(String isin) {
        for (int i = 0; i < instrumentList.size(); i++) {
            if (instrumentList.get(i).getIsin().equals(isin)) {
                instrumentList.remove(i);
                log.info("Removed instrument: " + isin);
                break;
            }
        }
        // Call the function to remove quotes with given isin
        quoteDAO.removeQuotesByIsin(isin);
    }

    public List<Instrument> getInstrumentList() {
        return instrumentList;
    }


}
