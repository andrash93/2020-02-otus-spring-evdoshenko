package ru.mango.vpbx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mango.vpbx.dto.CallEvent;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.dto.vpbx.VpbxSummaryDto;
import ru.mango.vpbx.events.CallEventHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
public class VpbxController {

    private final Logger logger = LoggerFactory.getLogger(VpbxController.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final CallEventHandler callEventHandler;

    public VpbxController(CallEventHandler callEventHandler) {
        this.callEventHandler = callEventHandler;
    }

    @PostMapping("/events/call")
    public void callEvent(HttpServletRequest req) {
        try {
            String[] jsons = req.getParameterMap().get("json");
            String[] vpbxApiKey = req.getParameterMap().get("vpbx_api_key");
            VpbxCallDto call = mapper.readValue(jsons[0], VpbxCallDto.class);
            logger.info("call event {}", call);

            callEventHandler.handle(MessageBuilder
                    .withPayload(new CallEvent(vpbxApiKey[0], call))
                    .build());
        } catch (IOException e) {
            logger.error("IOException ", e);
        }
    }

    @PostMapping("/events/summary")
    public void summaryEvent(HttpServletRequest req) {
        try {
            String[] jsons = req.getParameterMap().get("json");
            String[] vpbxApiKey = req.getParameterMap().get("vpbx_api_key");
            VpbxSummaryDto summary = mapper.readValue(jsons[0], VpbxSummaryDto.class);
            logger.info("summary event {}", summary);
            callEventHandler.handle(MessageBuilder
                    .withPayload(new CallEvent(vpbxApiKey[0], summary))
                    .build());
        } catch (IOException e) {
            logger.error("IOException ", e);
        }
    }

}
