package com.tr.eren.tradedataservice.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoQuotesFoundException extends RuntimeException {

    // Throw this exception if no quotes with given ISIN is found
    public NoQuotesFoundException(String message) {
        super(message);
        log.error("Resource not found: " + message);
    }
}
