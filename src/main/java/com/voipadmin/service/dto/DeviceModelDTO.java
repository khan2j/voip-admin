package com.voipadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Lob;
import com.voipadmin.domain.enumeration.DeviceType;

/**
 * A DTO for the {@link com.voipadmin.domain.DeviceModel} entity.
 */
public class DeviceModelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean isConfigurable;

    private Integer linesCount;

    @Lob
    private byte[] configTemplate;

    private String configTemplateContentType;
    @Lob
    private byte[] firmwareFile;

    private String firmwareFileContentType;
    private DeviceType deviceType;


    private Long otherDeviceTypeId;
    private String otherDeviceTypeName;

    private Long vendorId;
    private String vendorName;

    private Set<OptionDTO> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsConfigurable() {
        return isConfigurable;
    }

    public void setIsConfigurable(Boolean isConfigurable) {
        this.isConfigurable = isConfigurable;
    }

    public Integer getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(Integer linesCount) {
        this.linesCount = linesCount;
    }

    public byte[] getConfigTemplate() {
        return configTemplate;
    }

    public void setConfigTemplate(byte[] configTemplate) {
        this.configTemplate = configTemplate;
    }

    public String getConfigTemplateContentType() {
        return configTemplateContentType;
    }

    public void setConfigTemplateContentType(String configTemplateContentType) {
        this.configTemplateContentType = configTemplateContentType;
    }

    public byte[] getFirmwareFile() {
        return firmwareFile;
    }

    public void setFirmwareFile(byte[] firmwareFile) {
        this.firmwareFile = firmwareFile;
    }

    public String getFirmwareFileContentType() {
        return firmwareFileContentType;
    }

    public void setFirmwareFileContentType(String firmwareFileContentType) {
        this.firmwareFileContentType = firmwareFileContentType;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public Long getOtherDeviceTypeId() {
        return otherDeviceTypeId;
    }

    public void setOtherDeviceTypeId(Long otherDeviceTypeId) {
        this.otherDeviceTypeId = otherDeviceTypeId;
    }

    public String getOtherDeviceTypeName() {
        return otherDeviceTypeName;
    }

    public void setOtherDeviceTypeName(String otherDeviceTypeName) {
        this.otherDeviceTypeName = otherDeviceTypeName;
    }

    public Set<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionDTO> options) {
        this.options = options;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeviceModelDTO)) {
            return false;
        }

        return id != null && id.equals(((DeviceModelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeviceModelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isConfigurable='" + isIsConfigurable() + "'" +
            ", linesCount=" + getLinesCount() +
            ", configTemplate='" + getConfigTemplate() + "'" +
            ", firmwareFile='" + getFirmwareFile() + "'" +
            ", deviceType='" + getDeviceType() + "'" +
            ", otherDeviceTypeId=" + getOtherDeviceTypeId() +
            "}";
    }
}
