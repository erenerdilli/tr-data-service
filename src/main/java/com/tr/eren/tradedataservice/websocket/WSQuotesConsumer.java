package com.tr.eren.tradedataservice.websocket;

import com.tr.eren.tradedataservice.common.handler.MessageHandler;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.ExecutionException;

public class WSQuotesConsumer extends TextWebSocketHandler {
    @Value("${websocket.uri.quotes}")
    private String wsUri; // value can't be set from properties

    @Autowired
    private MessageHandler messageHandler;

    @Getter
    private WebSocketSession quotesClientSession;

    public WSQuotesConsumer() throws ExecutionException, InterruptedException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        this.quotesClientSession = webSocketClient
                .doHandshake(this, new WebSocketHttpHeaders(), URI.create("ws://localhost:8032/quotes")).get();
    }

    @SneakyThrows
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException {

        String quoteUpdateJson = message.getPayload();

        // Send the message string to message handler
        messageHandler.handleQuoteMessage(quoteUpdateJson);

    }
}
