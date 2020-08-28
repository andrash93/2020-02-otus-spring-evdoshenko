package ru.mango.server.service;

import ru.mango.common.model.vpbx.Call;

import java.util.List;

public interface VpbxService {

    List<Call> getCurrentCalls(String vpbxApiKey);
}
