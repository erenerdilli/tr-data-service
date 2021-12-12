package com.tr.eren.tradedataservice.websocket;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class WebsocketSetup {

    @EventListener(ApplicationReadyEvent.class)
    public void initWebSocketConsumers() {
        try {
            WSInstrumentsConsumer webSocketConsumer = new WSInstrumentsConsumer();
            webSocketConsumer.getInstrumentsClientSession().sendMessage(new TextMessage("initialize"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            WSQuotesConsumer webSocketConsumer = new WSQuotesConsumer();
            webSocketConsumer.getQuotesClientSession().sendMessage(new TextMessage("initialize"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
