package com.voipadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.voipadmin.domain.VoipAccount} entity.
 */
public class VoipAccountDTO implements Serializable {

    private Long id;

    private Boolean manuallyCreated;

    @NotNull
    private String username;

    private String password;

    private String sipServer;

    private String sipPort;

    private Boolean lineEnable;

    private Integer lineNumber;


    private Long asteriskAccountId;

    private Long deviceId;

    private String deviceMac;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isManuallyCreated() {
        return manuallyCreated;
    }

    public void setManuallyCreated(Boolean manuallyCreated) {
        this.manuallyCreated = manuallyCreated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSipServer() {
        return sipServer;
    }

    public void setSipServer(String sipServer) {
        this.sipServer = sipServer;
    }

    public String getSipPort() {
        return sipPort;
    }

    public void setSipPort(String sipPort) {
        this.sipPort = sipPort;
    }

    public Boolean isLineEnable() {
        return lineEnable;
    }

    public void setLineEnable(Boolean lineEnable) {
        this.lineEnable = lineEnable;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getAsteriskAccountId() {
        return asteriskAccountId;
    }

    public void setAsteriskAccountId(Long asteriskAccountId) {
        this.asteriskAccountId = asteriskAccountId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoipAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((VoipAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoipAccountDTO{" +
            "id=" + getId() +
            ", manuallyCreated='" + isManuallyCreated() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", sipServer='" + getSipServer() + "'" +
            ", sipPort='" + getSipPort() + "'" +
            ", lineEnable='" + isLineEnable() + "'" +
            ", lineNumber='" + getLineNumber() + "'" +
            ", asteriskAccountId=" + getAsteriskAccountId() +
            ", deviceId=" + getDeviceId() +
            ", deviceMac='" + getDeviceMac() + "'" +
            "}";
    }
}
