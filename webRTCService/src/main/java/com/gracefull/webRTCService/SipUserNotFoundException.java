package com.gracefull.webRTCService;

public class SipUserNotFoundException extends RuntimeException {

    SipUserNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
