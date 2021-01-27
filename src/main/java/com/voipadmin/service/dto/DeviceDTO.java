package com.voipadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.voipadmin.domain.enumeration.ProvisioningMode;

/**
 * A DTO for the {@link com.voipadmin.domain.Device} entity.
 */
public class DeviceDTO implements Serializable {

    private Long id;

    @NotNull
    private String mac;

    private String inventoryNumber;

    private String location;

    private String hostname;

    private String webLogin;

    private String webPassword;

    private Boolean dhcpEnabled;

    private String ipAddress;

    private String subnetMask;

    private String defaultGw;

    private String dns1;

    private String dns2;

    private ProvisioningMode provisioningMode;

    private String provisioningUrl;

    private String ntpServer;

    private String notes;


    private Long modelId;

    private String modelName;

    private Long responsiblePersonId;

    private String responsiblePersonLastName;

    private String responsiblePersonFullName;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getWebLogin() {
        return webLogin;
    }

    public void setWebLogin(String webLogin) {
        this.webLogin = webLogin;
    }

    public String getWebPassword() {
        return webPassword;
    }

    public void setWebPassword(String webPassword) {
        this.webPassword = webPassword;
    }

    public Boolean isDhcpEnabled() {
        return dhcpEnabled;
    }

    public void setDhcpEnabled(Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getDefaultGw() {
        return defaultGw;
    }

    public void setDefaultGw(String defaultGw) {
        this.defaultGw = defaultGw;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public ProvisioningMode getProvisioningMode() {
        return provisioningMode;
    }

    public void setProvisioningMode(ProvisioningMode provisioningMode) {
        this.provisioningMode = provisioningMode;
    }

    public String getProvisioningUrl() {
        return provisioningUrl;
    }

    public void setProvisioningUrl(String provisioningUrl) {
        this.provisioningUrl = provisioningUrl;
    }

    public String getNtpServer() {
        return ntpServer;
    }

    public void setNtpServer(String ntpServer) {
        this.ntpServer = ntpServer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long deviceModelId) {
        this.modelId = deviceModelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String deviceModelName) {
        this.modelName = deviceModelName;
    }

    public Long getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(Long responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public String getResponsiblePersonLastName() {
        return responsiblePersonLastName;
    }

    public void setResponsiblePersonLastName(String responsiblePersonLastName) {
        this.responsiblePersonLastName = responsiblePersonLastName;
    }

    public String getResponsiblePersonFullName() {
        return responsiblePersonFullName;
    }

    public void setResponsiblePersonFullName(String responsiblePersonFullName) {
        this.responsiblePersonFullName = responsiblePersonFullName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long deviceId) {
        this.parentId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceDTO{" +
            "id=" + getId() +
            ", mac='" + getMac() + "'" +
            ", inventoryNumber='" + getInventoryNumber() + "'" +
            ", location='" + getLocation() + "'" +
            ", hostname='" + getHostname() + "'" +
            ", webLogin='" + getWebLogin() + "'" +
            ", webPassword='" + getWebPassword() + "'" +
            ", dhcpEnabled='" + isDhcpEnabled() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", subnetMask='" + getSubnetMask() + "'" +
            ", defaultGw='" + getDefaultGw() + "'" +
            ", dns1='" + getDns1() + "'" +
            ", dns2='" + getDns2() + "'" +
            ", provisioningMode='" + getProvisioningMode() + "'" +
            ", provisioningUrl='" + getProvisioningUrl() + "'" +
            ", ntpServer='" + getNtpServer() + "'" +
            ", notes='" + getNotes() + "'" +
            ", modelId=" + getModelId() +
            ", modelName='" + getModelName() + "'" +
            ", responsiblePersonId=" + getResponsiblePersonId() +
            ", responsiblePersonLastName='" + getResponsiblePersonLastName() + "'" +
            ", parentId=" + getParentId() +
            "}";
    }
}
