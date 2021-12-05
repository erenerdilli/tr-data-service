package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.api.model.Instrument;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDAO {
    /*
    * Datasource for Instruments
     */
    public static List<Instrument> instrumentList = new ArrayList<>();


    public static Instrument saveInstrument(Instrument instrument) throws InvalidAttributeValueException {
        if (instrument != null) {
            instrumentList.add(instrument);
        }
        return instrument;
    }

    public static void removeInstrumentByIsin(String isin) {
        for (int i = 0; i < instrumentList.size(); i++) {
            if (instrumentList.get(i).getIsin().equals(isin)) {
                instrumentList.remove(i);
                break;
            }
        }
        // TODO: Call the function to remove quotes with given isin
    }


}
