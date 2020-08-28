package ru.mango.vpbx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mango.common.model.vpbx.Call;
import ru.mango.vpbx.service.CallService;

import java.util.List;

@RestController
public class CallController {

    private final Logger logger = LoggerFactory.getLogger(CallController.class);

    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/calls/{vpbxApiKey}")
    public List<Call> getCall(@PathVariable String vpbxApiKey) {
        return callService.getCurrentCalls(vpbxApiKey);
    }

    @GetMapping("/calls/delete/{vpbxApiKey}")
    public String deleteCall(@PathVariable String vpbxApiKey) {
         callService.deleteCalls(vpbxApiKey);
         return "delete success";
    }


}
