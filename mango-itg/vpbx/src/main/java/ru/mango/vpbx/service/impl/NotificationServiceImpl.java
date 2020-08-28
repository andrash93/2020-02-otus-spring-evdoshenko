package ru.mango.vpbx.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ru.mango.common.model.vpbx.Call;
import ru.mango.vpbx.config.ServiceLocationConfig;
import ru.mango.vpbx.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final RestTemplate restTemplate;
    private final ServiceLocationConfig serviceLocationConfig;

    private final String CALL_FINISH = "/calls/finish";

    public NotificationServiceImpl(ServiceLocationConfig serviceLocationConfig) {
        this.serviceLocationConfig = serviceLocationConfig;
        restTemplate = new RestTemplate();
    }

    @Override
    public void notifyFinishCall(Call call) {
        logger.info("notify finish call {}", call);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> body = new HttpEntity(call, headers);
        try {
            String url = serviceLocationConfig.getItgServerService() + CALL_FINISH;
            ResponseEntity<String> response = restTemplate.postForEntity(url, body, String.class);

            if (response.getBody() == null) {
                logger.error("ERROR in {} response body = null", CALL_FINISH);
            }

            logger.info("notifyFinishCall response body = {}", response.getBody());
        } catch (Exception e) {
            logger.info("error in notifyFinishCall {}", e.getMessage());
        }
    }
}
