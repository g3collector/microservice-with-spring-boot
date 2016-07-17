package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageRestController {

    @RequestMapping(method = RequestMethod.GET, value = "/message")
    String read() {
        return message;
    }

    private final String message;

    @Autowired
    public MessageRestController(@Value("${message}") String message) {
        this.message = message;
    }
}
