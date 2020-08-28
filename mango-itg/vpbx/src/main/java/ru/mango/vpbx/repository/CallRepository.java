package ru.mango.vpbx.repository;

import ru.mango.vpbx.dto.vpbx.VpbxCallDto;

import java.util.List;
import java.util.Optional;

public interface CallRepository {

    Optional<VpbxCallDto> getCall(String vpbxApiKey, String entryId);

    List<VpbxCallDto> getCallEvents(String entryId);

    List<VpbxCallDto> getCalls(String vpbxApiKey);

    void saveCall(String vpbxApiKey, String entryId, VpbxCallDto call);

    void deleteCall(String vpbxApiKey, String entryId);

    void delete(String vpbxApiKey);
}
