package com.tr.eren.tradedataservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tr.eren.tradedataservice.api.dto.CandlestickDTO;
import com.tr.eren.tradedataservice.api.mapper.CandlestickDTOMapper;
import com.tr.eren.tradedataservice.api.model.Candlestick;
import com.tr.eren.tradedataservice.api.service.CandlestickService;
import com.tr.eren.tradedataservice.common.dao.InstrumentDAO;
import com.tr.eren.tradedataservice.common.dao.QuoteDAO;
import com.tr.eren.tradedataservice.common.dao.QuoteDAOImpl;
import com.tr.eren.tradedataservice.common.handler.MessageHandler;
import com.tr.eren.tradedataservice.common.mapper.JsonToPojoMapper;
import com.tr.eren.tradedataservice.common.model.Instrument;
import com.tr.eren.tradedataservice.common.model.InstrumentEvent;
import com.tr.eren.tradedataservice.common.model.Quote;
import com.tr.eren.tradedataservice.common.model.QuoteUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.InvalidAttributeValueException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TradeDataServiceApplicationTests {

	@Autowired
	private QuoteDAO quoteDAO;

	@Autowired
	private InstrumentDAO instrumentDAO;

	@Autowired
	private CandlestickService candlestickService;

	@Autowired
	private MessageHandler messageHandler;

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

	@Test
	void getQuotesByIsinTest() {
		List<Quote> quotes = quoteDAO.getQuotesByIsin("ST6610357281", LocalDateTime.now());
		assertThat(quotes.size()).isGreaterThan(0);
	}

	@Test
	void mapToEntityTest() {
		CandlestickDTO candlestickDTO = Mockito.mock(CandlestickDTO.class);
		Mockito.when(candlestickDTO.getOpenTimestamp()).thenReturn(LocalDateTime.of(2021, 12, 5, 20, 30));
		Mockito.when(candlestickDTO.getOpenPrice()).thenReturn(BigDecimal.valueOf(1000));
		Mockito.when(candlestickDTO.getHighPrice()).thenReturn(BigDecimal.valueOf(1500));
		Mockito.when(candlestickDTO.getLowPrice()).thenReturn(BigDecimal.valueOf(900));
		Mockito.when(candlestickDTO.getClosePrice()).thenReturn(BigDecimal.valueOf(1500));
		Mockito.when(candlestickDTO.getCloseTimestamp()).thenReturn(LocalDateTime.of(2021,12,5,20,31));

		Candlestick candlestick = CandlestickDTOMapper.mapToEntity(candlestickDTO);
		assertThat(candlestick.getOpenTimestamp()).isEqualTo(LocalDateTime.of(2021, 12, 5, 20, 30));
		assertThat(candlestick.getOpenPrice()).isEqualTo(BigDecimal.valueOf(1000));
		assertThat(candlestick.getHighPrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestick.getLowPrice()).isEqualTo(BigDecimal.valueOf(900));
		assertThat(candlestick.getClosePrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestick.getCloseTimestamp()).isEqualTo(LocalDateTime.of(2021,12,5,20,31));
	}

	@Test
	void candleStickTest() {
		Candlestick candlestick = Mockito.mock(Candlestick.class);
		Mockito.when(candlestick.getOpenTimestamp()).thenReturn(LocalDateTime.of(2021, 12, 5, 20, 30));
		Mockito.when(candlestick.getOpenPrice()).thenReturn(BigDecimal.valueOf(1000));
		Mockito.when(candlestick.getHighPrice()).thenReturn(BigDecimal.valueOf(1500));
		Mockito.when(candlestick.getLowPrice()).thenReturn(BigDecimal.valueOf(900));
		Mockito.when(candlestick.getClosePrice()).thenReturn(BigDecimal.valueOf(1500));
		Mockito.when(candlestick.getCloseTimestamp()).thenReturn(LocalDateTime.of(2021,12,5,20,31));

		assertThat(candlestick.getOpenTimestamp()).isEqualTo(LocalDateTime.of(2021, 12, 5, 20, 30));
		assertThat(candlestick.getOpenPrice()).isEqualTo(BigDecimal.valueOf(1000));
		assertThat(candlestick.getHighPrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestick.getLowPrice()).isEqualTo(BigDecimal.valueOf(900));
		assertThat(candlestick.getClosePrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestick.getCloseTimestamp()).isEqualTo(LocalDateTime.of(2021,12,5,20,31));

		Candlestick candlestick1 = new Candlestick(LocalDateTime.of(2021, 12, 5, 20, 30),
				BigDecimal.valueOf(1000),
				BigDecimal.valueOf(1500),
				BigDecimal.valueOf(900),
				BigDecimal.valueOf(1500),
				LocalDateTime.of(2021,12,5,20,31));
		assertThat(candlestick1.getOpenTimestamp()).isEqualTo(LocalDateTime.of(2021, 12, 5, 20, 30));
		assertThat(candlestick1.getOpenPrice()).isEqualTo(BigDecimal.valueOf(1000));
		assertThat(candlestick1.getHighPrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestick1.getLowPrice()).isEqualTo(BigDecimal.valueOf(900));
		assertThat(candlestick1.getClosePrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestick1.getCloseTimestamp()).isEqualTo(LocalDateTime.of(2021,12,5,20,31));

		Candlestick candlestick2 = new Candlestick();
		assertThat(candlestick2.getOpenTimestamp()).isNull();
		assertThat(candlestick2.getOpenPrice()).isNull();
		assertThat(candlestick2.getHighPrice()).isNull();
		assertThat(candlestick2.getLowPrice()).isNull();
		assertThat(candlestick2.getClosePrice()).isNull();
		assertThat(candlestick2.getCloseTimestamp()).isNull();

	}

	@Test
	void getDateTimeMinutesTest() {
		Quote quote = new Quote();
		quote.setIsin("TEST");
		quote.setPrice(BigDecimal.valueOf(1000));
		quote.setDateTime(LocalDateTime.of(2021,12,5,20,45, 30));

		assertThat(quote.getDateTimeMinutes()).isEqualTo(LocalDateTime.of(2021,12,5,20,45));
		assertThat(quote.getDateTime()).isEqualTo(LocalDateTime.of(2021,12,5,20,45, 30));
	}

	@Test
	void instrumentTest() {
		Instrument instrument = new Instrument("Lorem ipsum", "TEST");

		assertThat(instrument.getIsin()).isEqualTo("TEST");
		assertThat(instrument.getDescription()).isEqualTo("Lorem ipsum");
	}

	@Test
	void dtoMapperMapTest() {
		Candlestick candlestick = Mockito.mock(Candlestick.class);
		Mockito.when(candlestick.getOpenTimestamp()).thenReturn(LocalDateTime.of(2021, 12, 5, 20, 30));
		Mockito.when(candlestick.getOpenPrice()).thenReturn(BigDecimal.valueOf(1000));
		Mockito.when(candlestick.getHighPrice()).thenReturn(BigDecimal.valueOf(1500));
		Mockito.when(candlestick.getLowPrice()).thenReturn(BigDecimal.valueOf(900));
		Mockito.when(candlestick.getClosePrice()).thenReturn(BigDecimal.valueOf(1500));
		Mockito.when(candlestick.getCloseTimestamp()).thenReturn(LocalDateTime.of(2021,12,5,20,31));

		CandlestickDTO candlestickDTO = CandlestickDTOMapper.mapToDTO(candlestick);
		assertThat(candlestickDTO.getOpenTimestamp()).isEqualTo(LocalDateTime.of(2021, 12, 5, 20, 30));
		assertThat(candlestickDTO.getOpenPrice()).isEqualTo(BigDecimal.valueOf(1000));
		assertThat(candlestickDTO.getHighPrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestickDTO.getLowPrice()).isEqualTo(BigDecimal.valueOf(900));
		assertThat(candlestickDTO.getClosePrice()).isEqualTo(BigDecimal.valueOf(1500));
		assertThat(candlestickDTO.getCloseTimestamp()).isEqualTo(LocalDateTime.of(2021,12,5,20,31));

	}

	@Nested
	class TestNested {
		@BeforeEach
		void setup() {

		}

		@Test
		void handleInstrumentMessageTest() throws InvalidAttributeValueException {
			String messageDelete = "{ \"data\": { \"description\": \"convallis repudiandae iisque homero maiorum\", \"isin\": \"RG8P83C62E51\" }, \"type\": \"DELETE\" }";
			String messageAdd = "{ \"data\": { \"description\": \"convallis repudiandae iisque homero maiorum\", \"isin\": \"RG8P83C62E51\" }, \"type\": \"ADD\" }";
			String invalidMessage = "{ \"data\": { \"description\": \"convallis repudiandae iisque homero maiorum\", \"isin\": \"RG8P83C62E51\" }, \"type\": \"TEST\" }";

			messageHandler.handleInstrumentMessage(messageAdd);
			Instrument instrument = instrumentDAO.getInstrumentList().stream().filter(x -> x.getIsin().equals("RG8P83C62E51")).findFirst().get();

			assertThat(instrumentDAO.getInstrumentList().size()).isGreaterThan(0);
			assertThat(instrument.getIsin()).isEqualTo("RG8P83C62E51");
			assertThat(instrument.getDescription()).isEqualTo("convallis repudiandae iisque homero maiorum");

			messageHandler.handleInstrumentMessage(messageDelete);
			instrument = instrumentDAO.getInstrumentList().stream().filter(x -> x.getIsin().equals("RG8P83C62E51")).findFirst().orElse(null);
			assertThat(instrument).isNull();


			try {
				messageHandler.handleInstrumentMessage(invalidMessage);
			} catch (InvalidAttributeValueException e) {
				assertThat(e.getMessage()).startsWith("Invalid value: ");
			}

		}

		@Test
		void handleQuoteMessageTest() throws InvalidAttributeValueException {
			String messageOrigin = "{ \"data\": { \"price\": 1268.108, \"isin\": \"OY4208280445\" }, \"type\": \"QUOTE\" }";
			String messageUpdated = "{ \"data\": { \"price\": 1500.108, \"isin\": \"OY4208280445\" }, \"type\": \"QUOTE\" }";
			String invalidMessage = "{ \"data\": {{ \"price\": 1268.108, \"isin\": \"OY4208280445\" }, \"type\": \"QUOTE\" }";

			messageHandler.handleQuoteMessage(messageOrigin);
			assertThat(QuoteDAOImpl.quoteMap.containsKey("OY4208280445")).isTrue();

			messageHandler.handleQuoteMessage(messageUpdated);
			assertThat(QuoteDAOImpl.quoteMap.get("OY4208280445").size() > 0).isTrue();

			QuoteUpdate quoteUpdate = messageHandler.handleQuoteMessage(invalidMessage);
			assertThat(quoteUpdate).isNull();
		}
	}

	@Nested
	class TestService {

		@BeforeEach
		void setup() {
			instrumentDAO.getInstrumentList().add(new Instrument("lorem ipsum", "TESTISIN1"));
			instrumentDAO.getInstrumentList().add(new Instrument("dolor sit amet", "TESTISIN2"));

			quoteDAO.getQuoteMap().put("TESTISIN1",
					Arrays.asList(
							new Quote(BigDecimal.valueOf(100), "TESTISIN1", LocalDateTime.of(2021, 12, 12, 20, 30)),
							new Quote(BigDecimal.valueOf(200), "TESTISIN1", LocalDateTime.of(2021, 12, 12, 20, 30)),
							new Quote(BigDecimal.valueOf(150), "TESTISIN1", LocalDateTime.of(2021, 12, 12, 20, 30))
					));
			quoteDAO.getQuoteMap().put("TESTISIN2",
					Arrays.asList(
							new Quote(BigDecimal.valueOf(200), "TESTISIN2", LocalDateTime.of(2021, 12, 12, 20, 31)),
							new Quote(BigDecimal.valueOf(300), "TESTISIN2", LocalDateTime.of(2021, 12, 12, 20, 31)),
							new Quote(BigDecimal.valueOf(400), "TESTISIN2", LocalDateTime.of(2021, 12, 12, 20, 31))
					));
		}

		@Test
		void getCandlesticksTest() {
			CandlestickDTO fetchedCandlestick = candlestickService.getCandlesticks("TESTISIN1").get(0);
			assertThat(fetchedCandlestick.getOpenTimestamp()).isEqualTo(LocalDateTime.of(2021, 12, 12, 20, 30));
			assertThat(fetchedCandlestick.getOpenPrice()).isEqualTo(BigDecimal.valueOf(100));
			assertThat(fetchedCandlestick.getLowPrice()).isEqualTo(BigDecimal.valueOf(100));
			assertThat(fetchedCandlestick.getHighPrice()).isEqualTo(BigDecimal.valueOf(200));
			assertThat(fetchedCandlestick.getClosePrice()).isEqualTo(BigDecimal.valueOf(150));
			assertThat(fetchedCandlestick.getCloseTimestamp()).isEqualTo(LocalDateTime.of(2021, 12, 12, 20, 31));

		}

	}



}
