package ru.mango.vpbx.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mango.common.model.vpbx.Call;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.repository.CallRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CallServiceImplTest {

    @Mock
    private CallRepository callRepository;

    @InjectMocks
    private CallServiceImpl callService;

    @Test
    public void getCurrentCallsTest() {

        List<VpbxCallDto> calls = new ArrayList<>();
        calls.add(new VpbxCallDto());

        String vpbxApiKey = "3245345fdg";
        when(callRepository.getCalls(vpbxApiKey)).thenReturn(calls);

        String entryId = "345";
        List<VpbxCallDto> callEvents = callRepository.getCallEvents(entryId);
        when(callRepository.getCallEvents(entryId)).thenReturn(callEvents);

        List<Call> resultList = callService.getCurrentCalls(vpbxApiKey);

        assertEquals(1, resultList.size());
        verify(callRepository, times(1)).getCalls(vpbxApiKey);
    }
}