package com.tr.eren.tradedataservice.common.dao;

import com.tr.eren.tradedataservice.common.model.Instrument;

import javax.management.InvalidAttributeValueException;
import java.util.List;

public interface InstrumentDAO {

    Instrument saveInstrument(Instrument instrument) throws InvalidAttributeValueException;

    void removeInstrumentByIsin(String isin);

    List<Instrument> getInstrumentList();

}
