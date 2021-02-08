package com.voipadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.voipadmin.domain.enumeration.ProvisioningMode;

/**
 * A Device.
 */
@Entity
@Table(name = "device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "mac", nullable = false)
    private String mac;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @Column(name = "location")
    private String location;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "web_login")
    private String webLogin;

    @Column(name = "web_password")
    private String webPassword;

    @Column(name = "dhcp_enabled")
    private Boolean dhcpEnabled;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "subnet_mask")
    private String subnetMask;

    @Column(name = "default_gw")
    private String defaultGw;

    @Column(name = "dns_1")
    private String dns1;

    @Column(name = "dns_2")
    private String dns2;

    @Enumerated(EnumType.STRING)
    @Column(name = "provisioning_mode")
    private ProvisioningMode provisioningMode;

    @Column(name = "provisioning_url")
    private String provisioningUrl;

    @Column(name = "ntp_server")
    private String ntpServer;

    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Setting> settings = new HashSet<>();

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<VoipAccount> voipAccounts = new HashSet<>();

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Device> children = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "devices", allowSetters = true)
    private DeviceModel model;

    @ManyToOne
    @JsonIgnoreProperties(value = "devices", allowSetters = true)
    private ResponsiblePerson responsiblePerson;

    @ManyToOne
    @JsonIgnoreProperties(value = "children", allowSetters = true)
    private Device parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public Device mac(String mac) {
        this.mac = mac;
        return this;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public Device inventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
        return this;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getLocation() {
        return location;
    }

    public Device location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHostname() {
        return hostname;
    }

    public Device hostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getWebLogin() {
        return webLogin;
    }

    public Device webLogin(String webLogin) {
        this.webLogin = webLogin;
        return this;
    }

    public void setWebLogin(String webLogin) {
        this.webLogin = webLogin;
    }

    public String getWebPassword() {
        return webPassword;
    }

    public Device webPassword(String webPassword) {
        this.webPassword = webPassword;
        return this;
    }

    public void setWebPassword(String webPassword) {
        this.webPassword = webPassword;
    }

    public Boolean isDhcpEnabled() {
        return dhcpEnabled;
    }

    public Device dhcpEnabled(Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
        return this;
    }

    public void setDhcpEnabled(Boolean dhcpEnabled) {
        this.dhcpEnabled = dhcpEnabled;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Device ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public Device subnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
        return this;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getDefaultGw() {
        return defaultGw;
    }

    public Device defaultGw(String defaultGw) {
        this.defaultGw = defaultGw;
        return this;
    }

    public void setDefaultGw(String defaultGw) {
        this.defaultGw = defaultGw;
    }

    public String getDns1() {
        return dns1;
    }

    public Device dns1(String dns1) {
        this.dns1 = dns1;
        return this;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public Device dns2(String dns2) {
        this.dns2 = dns2;
        return this;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public ProvisioningMode getProvisioningMode() {
        return provisioningMode;
    }

    public Device provisioningMode(ProvisioningMode provisioningMode) {
        this.provisioningMode = provisioningMode;
        return this;
    }

    public void setProvisioningMode(ProvisioningMode provisioningMode) {
        this.provisioningMode = provisioningMode;
    }

    public String getProvisioningUrl() {
        return provisioningUrl;
    }

    public Device provisioningUrl(String provisioningUrl) {
        this.provisioningUrl = provisioningUrl;
        return this;
    }

    public void setProvisioningUrl(String provisioningUrl) {
        this.provisioningUrl = provisioningUrl;
    }

    public String getNtpServer() {
        return ntpServer;
    }

    public Device ntpServer(String ntpServer) {
        this.ntpServer = ntpServer;
        return this;
    }

    public void setNtpServer(String ntpServer) {
        this.ntpServer = ntpServer;
    }

    public String getNotes() {
        return notes;
    }

    public Device notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Setting> getSettings() {
        return settings;
    }

    public Device settings(Set<Setting> settings) {
        this.settings = settings;
        return this;
    }

    public Device addSettings(Setting setting) {
        this.settings.add(setting);
        setting.setDevice(this);
        return this;
    }

    public Device removeSettings(Setting setting) {
        this.settings.remove(setting);
        setting.setDevice(null);
        return this;
    }

    public void setSettings(Set<Setting> settings) {
        this.settings = settings;
    }

    public Set<VoipAccount> getVoipAccounts() {
        return voipAccounts;
    }

    public Device voipAccounts(Set<VoipAccount> voipAccounts) {
        this.voipAccounts = voipAccounts;
        return this;
    }

    public Device addVoipAccounts(VoipAccount voipAccount) {
        this.voipAccounts.add(voipAccount);
        voipAccount.setDevice(this);
        return this;
    }

    public Device removeVoipAccounts(VoipAccount voipAccount) {
        this.voipAccounts.remove(voipAccount);
        voipAccount.setDevice(null);
        return this;
    }

    public void setVoipAccounts(Set<VoipAccount> voipAccounts) {
        this.voipAccounts = voipAccounts;
    }

    public Set<Device> getChildren() {
        return children;
    }

    public Device children(Set<Device> devices) {
        this.children = devices;
        return this;
    }

    public Device addChildren(Device device) {
        this.children.add(device);
        device.setParent(this);
        return this;
    }

    public Device removeChildren(Device device) {
        this.children.remove(device);
        device.setParent(null);
        return this;
    }

    public void setChildren(Set<Device> devices) {
        this.children = devices;
    }

    public DeviceModel getModel() {
        return model;
    }

    public Device model(DeviceModel deviceModel) {
        this.model = deviceModel;
        return this;
    }

    public void setModel(DeviceModel deviceModel) {
        this.model = deviceModel;
    }

    public ResponsiblePerson getResponsiblePerson() {
        return responsiblePerson;
    }

    public Device responsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
        return this;
    }

    public void setResponsiblePerson(ResponsiblePerson responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Device getParent() {
        return parent;
    }

    public Device parent(Device device) {
        this.parent = device;
        return this;
    }

    public void setParent(Device device) {
        this.parent = device;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Device)) {
            return false;
        }
        return id != null && id.equals(((Device) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Device{" +
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
            "}";
    }
}
