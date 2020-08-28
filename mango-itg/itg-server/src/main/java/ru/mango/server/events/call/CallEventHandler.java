package ru.mango.server.events.call;

import ru.mango.common.model.vpbx.Call;

public interface CallEventHandler {

    void handle(Call call);

}
