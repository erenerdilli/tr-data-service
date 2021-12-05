package com.tr.eren.tradedataservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tr.eren.tradedataservice.api.model.InstrumentEvent;
import com.tr.eren.tradedataservice.api.model.QuoteUpdate;
import com.tr.eren.tradedataservice.common.mapper.JsonToPojoMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class TradeDataServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mapInstrumentEventTest() throws JsonProcessingException {
		InstrumentEvent instrumentEvent = JsonToPojoMapper.mapInstrumentEvent("{ \"data\": { \"description\": \"neque ipsum melius tibique liber\", \"isin\": \"ETP087B48658\" }, \"type\": \"DELETE\" }");
		assertThat(instrumentEvent.getType()).isEqualTo(InstrumentEvent.Type.DELETE);
		assertThat(instrumentEvent.getData().getIsin()).isEqualTo("ETP087B48658");
		assertThat(instrumentEvent.getData().getDescription()).isEqualTo("neque ipsum melius tibique liber");
	}

	@Test
	void mapQuoteUpdateTest() throws JsonProcessingException {
		QuoteUpdate quoteUpdate = JsonToPojoMapper.mapQuteUpdate("{ \"data\": { \"price\": 489, \"isin\": \"YP06O2642686\" }, \"type\": \"QUOTE\" }");
		assertThat(quoteUpdate.getType()).isEqualTo(QuoteUpdate.Type.QUOTE);
		assertThat(quoteUpdate.getData().getIsin()).isEqualTo("YP06O2642686");
		assertThat(quoteUpdate.getData().getPrice()).isEqualTo(BigDecimal.valueOf(489));
	}

}
