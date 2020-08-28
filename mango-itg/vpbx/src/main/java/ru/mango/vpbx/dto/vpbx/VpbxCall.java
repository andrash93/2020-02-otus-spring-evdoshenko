package ru.mango.vpbx.dto.vpbx;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class VpbxCall {
    
    @JsonProperty("entry_id")
    protected String entryId;

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
}
