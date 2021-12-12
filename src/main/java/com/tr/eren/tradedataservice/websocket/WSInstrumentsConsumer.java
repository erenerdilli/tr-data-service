package com.tr.eren.tradedataservice.websocket;

import com.tr.eren.tradedataservice.common.handler.MessageHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@Service
public class WSInstrumentsConsumer extends TextWebSocketHandler {

    @Value("${websocket.uri.instruments}")
    private String wsUri; // value can't be set from properties

    @Autowired
    private MessageHandler messageHandler;

    @Getter
    private WebSocketSession instrumentsClientSession;

        public WSInstrumentsConsumer() throws ExecutionException, InterruptedException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        this.instrumentsClientSession = webSocketClient
                .doHandshake(this, new WebSocketHttpHeaders(), URI.create("ws://localhost:8032/instruments")).get();
    }

    @SneakyThrows
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException {

        String instrumentEventJson = message.getPayload();

        // Send the message string to message handler
        messageHandler.handleInstrumentMessage(instrumentEventJson);
    }

}
