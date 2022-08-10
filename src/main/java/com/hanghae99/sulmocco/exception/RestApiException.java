package com.hanghae99.sulmocco.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String message;
    private Boolean response;
}