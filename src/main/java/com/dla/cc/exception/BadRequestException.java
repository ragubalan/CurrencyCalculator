package com.dla.cc.exception;

import com.dla.cc.utils.JSONObjectUtils;

/**
 * Exception thrown whenever a response of 400 is expected with JSON response body.
 */
public class BadRequestException extends RuntimeException {
    /**
     * Constructor to respond with error code and error message.
     *
     * @param errorCode    : error code.
     * @param errorMessage : error message.
     */
    public BadRequestException(final String errorCode, final String errorMessage) {
        super(JSONObjectUtils.createErrorJson(errorCode, errorMessage));
    }
}
