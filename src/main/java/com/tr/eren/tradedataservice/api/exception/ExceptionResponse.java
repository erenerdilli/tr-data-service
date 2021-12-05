package com.tr.eren.tradedataservice.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/*
**
* Model for returning exception responses
 */

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
