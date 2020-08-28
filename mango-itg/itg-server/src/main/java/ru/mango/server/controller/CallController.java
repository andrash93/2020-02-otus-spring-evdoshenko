package ru.mango.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.mango.common.model.vpbx.Call;
import ru.mango.server.events.call.CallEventHandler;


@RestController
@RequestMapping("/calls")
public class CallController {

    private final Logger logger = LoggerFactory.getLogger(CallController.class);

    private final CallEventHandler callEventHandler;

    public CallController(CallEventHandler callEventHandler) {
        this.callEventHandler = callEventHandler;
    }

    @PostMapping("/finish")
    public String finishCall(@RequestBody Call call) {
        logger.info("invoke finishCall {}", call);
        callEventHandler.handle(call);
        return "success"; //todo понять яе возвращать
    }

}
