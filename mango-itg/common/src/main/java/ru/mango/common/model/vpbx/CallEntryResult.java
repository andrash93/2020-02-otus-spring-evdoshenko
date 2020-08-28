package ru.mango.common.model.vpbx;

public enum CallEntryResult {

    ACCEPTED(1),
    MISSED(0),
    ;

    private final Integer result;

    CallEntryResult(Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public static CallEntryResult getCallEntryResult(int result) {
        if (result == 0) {
            return MISSED;
        }
        return ACCEPTED;
    }
}
