package ru.mango.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mango.common.model.vpbx.Call;
import ru.mango.common.model.vpbx.CallDirection;
import ru.mango.common.model.vpbx.CallEntryResult;
import ru.mango.common.model.vpbx.CallLocation;
import ru.mango.common.model.vpbx.CallState;
import ru.mango.server.config.ServiceLocationConfig;
import ru.mango.server.service.VpbxService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class VpbxServiceImpl implements VpbxService {

    private final Logger logger = LoggerFactory.getLogger(VpbxServiceImpl.class);
    private final String GET_CURRENT_CALLS = "/calls/";

    private final RestTemplate restTemplate;
    private final ServiceLocationConfig serviceLocationConfig;

    public VpbxServiceImpl(ServiceLocationConfig serviceLocationConfig) {
        this.restTemplate = new RestTemplate();
        this.serviceLocationConfig = serviceLocationConfig;
    }

    @Override
    public List<Call> getCurrentCalls(String vpbxApiKey) {
        logger.info("vpbxApiKey {} getCurrentCalls", vpbxApiKey);
        try {
            String url = serviceLocationConfig.getVpbxService() + GET_CURRENT_CALLS + "/" + vpbxApiKey; //todo так нельзя делать
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            logger.info("notifyFinishCall response body = {}", response.getBody());
            List body = response.getBody();
            List<Call> currentCalls = new ArrayList<>();
            for (Object call : body) {
                currentCalls.add(getCall((Map) call));
            }
            return currentCalls;
        } catch (Exception e) {
            logger.info("error in getCurrentCalls {}", e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    private Call getCall(Map callMap) {
        Call call = new Call();
        call.setVpbxApiKey((String) callMap.get("vpbxApiKey"));
        if (callMap.get("startTime") != null) {
            call.setStartTime(Long.parseLong(String.valueOf(callMap.get("startTime"))));
        }
        if (callMap.get("connectedTime") != null) {
            call.setConnectedTime(Long.parseLong(String.valueOf(callMap.get("connectedTime"))));
        }
        if (callMap.get("talkTime") != null) {
            call.setTalkTime(Long.parseLong(String.valueOf(callMap.get("talkTime"))));
        }
        call.setPhoneFrom((String) callMap.get("phoneFrom"));
        call.setPhoneTo((String) callMap.get("phoneTo"));
        call.setExt((String) callMap.get("ext"));
        call.setLocation(CallLocation.valueOf((String) callMap.get("location")));
        call.setCallState(CallState.valueOf((String) callMap.get("callState")));
        call.setCallDirection(CallDirection.valueOf((String) callMap.get("callDirection")));
        return call;
    }
}
