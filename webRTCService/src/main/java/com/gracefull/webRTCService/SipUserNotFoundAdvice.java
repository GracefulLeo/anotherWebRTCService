package com.gracefull.webRTCService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SipUserNotFoundAdvice extends RuntimeException {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SipUserNotFoundException.class)
    String EmployeeNotFoundException(SipUserNotFoundException ex) {
        return ex.getMessage();
    }
}
