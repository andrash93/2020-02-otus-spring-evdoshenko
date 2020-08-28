package ru.mango.server.service.impl;

import org.springframework.stereotype.Service;
import ru.mango.common.model.vpbx.Call;
import ru.mango.server.repository.CallRepository;
import ru.mango.server.service.CallService;

@Service
public class CallServiceImpl implements CallService {

    private final CallRepository callRepository;

    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public void saveCall(Call call) {
        callRepository.save(call);
    }
}
