package ru.mango.vpbx.service;

import ru.mango.common.model.vpbx.Call;
import ru.mango.vpbx.dto.vpbx.VpbxCallDto;
import ru.mango.vpbx.dto.vpbx.VpbxSummaryDto;

import java.util.List;

public interface CallService {

    void updateCallState(String vpbxApiKey, VpbxCallDto vpbxCall);

    void finishCall(String vpbxApiKey, VpbxSummaryDto vpbxSummaryDto);

    List<Call> getCurrentCalls(String vpbxApiKey);

    void deleteCalls(String vpbxApiKey);

}
