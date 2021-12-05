package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.api.model.Instrument;

import javax.management.InvalidAttributeValueException;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDAO {
    public static List<Instrument> instrumentList = new ArrayList<>();

//    public static void groupList() {
//        Map<LocalDateTime, List<Quote>> map = quoteList.stream().collect(Collectors.groupingBy(Quote::getDateTimeMinutes));
//    }

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
    }


}
