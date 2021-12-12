package com.tr.eren.tradedataservice;

import com.tr.eren.tradedataservice.websocket.WSInstrumentsConsumer;
import com.tr.eren.tradedataservice.websocket.WSQuotesConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class TradeDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeDataServiceApplication.class, args);
	}

}
