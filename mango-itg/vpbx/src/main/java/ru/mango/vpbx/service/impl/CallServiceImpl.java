package ru.mango.vpbx.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mango.common.model.vpbx.Call;
import ru.mango.common.model.vpbx.CallDirection;
import ru.mango.common.model.vpbx.CallEntryResult;
import ru.mango.common.model.vpbx.CallLocation;
import ru.mango.common.model.vpbx.CallState;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.dto.vpbx.VpbxSummaryDto;
import ru.mango.vpbx.repository.CallRepository;
import ru.mango.vpbx.service.CallService;
import ru.mango.vpbx.service.NotificationService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallServiceImpl implements CallService {

    private final Logger logger = LoggerFactory.getLogger(CallServiceImpl.class);

    private final CallRepository callRepository;
    private final NotificationService notificationService;

    public CallServiceImpl(CallRepository callRepository, NotificationService notificationService) {
        this.callRepository = callRepository;
        this.notificationService = notificationService;
    }

    @Override
    public List<Call> getCurrentCalls(String vpbxApiKey) {
        List<Call> currentCalls = new ArrayList<>();
        List<VpbxCallDto> calls = callRepository.getCalls(vpbxApiKey);
        for (VpbxCallDto currentCall : calls) {
            List<VpbxCallDto> callEvents = callRepository.getCallEvents(currentCall.getEntryId());
            Optional<Call> call = getCurrentCall(callEvents);
            call.ifPresent(currentCalls::add);
        }
        return currentCalls;
    }

    @Override
    public void deleteCalls(String vpbxApiKey) {
        callRepository.delete(vpbxApiKey);
    }

    @Override
    public void updateCallState(String vpbxApiKey, VpbxCallDto vpbxCall) {
        callRepository.saveCall(vpbxApiKey, vpbxCall.getEntryId(), vpbxCall);
    }

    @Override
    public void finishCall(String vpbxApiKey, VpbxSummaryDto vpbxSummaryDto) {
        Call call = new Call();
        call.setVpbxApiKey(vpbxApiKey);
        call.setStartTime(Long.valueOf(vpbxSummaryDto.getCreateTime()));
        call.setTalkTime(Long.valueOf(vpbxSummaryDto.getTalkTime()));
        call.setEndTime(Long.valueOf(vpbxSummaryDto.getEndTime()));
        call.setCallDirection(CallDirection.getCallDirection(vpbxSummaryDto.getCallDirection()));
        call.setEntryResult(CallEntryResult.getCallEntryResult(vpbxSummaryDto.getEntryResult()));
        if (!vpbxSummaryDto.getFrom().getNumber().contains("sip") || vpbxSummaryDto.getFrom().getLineNumber() == null) {
            call.setPhoneFrom(vpbxSummaryDto.getFrom().getNumber());
        } else {
            call.setPhoneFrom(vpbxSummaryDto.getFrom().getLineNumber());
        }
        if (!vpbxSummaryDto.getTo().getNumber().contains("sip") || vpbxSummaryDto.getTo().getLineNumber() == null) {
            call.setPhoneTo(vpbxSummaryDto.getTo().getNumber());
        } else {
            call.setPhoneTo(vpbxSummaryDto.getTo().getLineNumber());
        }
        if (call.getCallDirection() == CallDirection.inbound) {
            call.setExt(vpbxSummaryDto.getTo().getExtension());
        } else {
            call.setExt(vpbxSummaryDto.getFrom().getExtension());
        }
        notificationService.notifyFinishCall(call);
        callRepository.deleteCall(vpbxApiKey, vpbxSummaryDto.getEntryId());
    }

    private Optional<Call> getCurrentCall(List<VpbxCallDto> callEvents) {
        try {
            Call call = new Call();

            List<VpbxCallDto> sortEvents = callEvents.stream().filter(callDto -> callDto.getCallState() != CallState.Disconnected).sorted(Comparator.comparing(VpbxCallDto::getTimestamp)).collect(Collectors.toList());
            Optional<VpbxCallDto> firstIvrEvent = sortEvents.stream().filter(callDto -> callDto.getLocation() == CallLocation.ivr).findFirst();
            List<VpbxCallDto> callAbonentAppearedEvents = sortEvents.stream().filter(callDto -> callDto.getLocation() == CallLocation.abonent
                    && callDto.getCallState() == CallState.Appeared).collect(Collectors.toList());
            List<VpbxCallDto> callAbonentConnectedEvents = sortEvents.stream().filter(callDto -> callDto.getLocation() == CallLocation.abonent
                    && callDto.getCallState() == CallState.Connected).collect(Collectors.toList());

            VpbxCallDto currentCallEvent = sortEvents.get(sortEvents.size() - 1);

            if (firstIvrEvent.isPresent()) {
                call.setStartTime(firstIvrEvent.get().getTimestamp());
                call.setTalkTime(System.currentTimeMillis() - firstIvrEvent.get().getTimestamp());
            } else {
                call.setStartTime(callAbonentAppearedEvents.get(0).getTimestamp());
                call.setTalkTime(System.currentTimeMillis() - callAbonentAppearedEvents.get(0).getTimestamp());
            }

            if (!callAbonentConnectedEvents.isEmpty()) {
                call.setConnectedTime(callAbonentConnectedEvents.get(0).getTimestamp());
            }

            call.setLocation(currentCallEvent.getLocation());
            call.setCallState(currentCallEvent.getCallState());
            call.setCallDirection(currentCallEvent.getCallDirection());

            if (!currentCallEvent.getFrom().getNumber().contains("sip") || currentCallEvent.getFrom().getLineNumber() == null) {
                call.setPhoneFrom(currentCallEvent.getFrom().getNumber());
            } else {
                call.setPhoneFrom(currentCallEvent.getFrom().getLineNumber());
            }

            if (!currentCallEvent.getTo().getNumber().contains("sip") || currentCallEvent.getTo().getLineNumber() == null) {
                call.setPhoneTo(currentCallEvent.getTo().getNumber());
            } else {
                call.setPhoneTo(currentCallEvent.getTo().getLineNumber());
            }

            if (call.getCallDirection() == CallDirection.inbound) {
                call.setExt(currentCallEvent.getTo().getExtension());
            } else {
                call.setExt(currentCallEvent.getFrom().getExtension());
            }

            return Optional.of(call);
        } catch (Exception e) {
            logger.error("Error in getCurrentCall ", e);
            return Optional.empty();
        }

    }
}
