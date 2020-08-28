package ru.mango.vpbx.dto.vpbx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VpbxCallPartyDto implements Serializable {

    @JsonProperty("number")
    private String number;
    @JsonProperty("line_number")
    private String lineNumber;
    @JsonProperty("extension")
    private String extension;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "CallPartyDto{" +
                "number='" + number + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
