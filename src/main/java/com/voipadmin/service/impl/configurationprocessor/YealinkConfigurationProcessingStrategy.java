package com.voipadmin.service.impl.configurationprocessor;

import com.voipadmin.domain.Device;
import com.voipadmin.domain.VoipAccount;
import com.voipadmin.domain.enumeration.OptionValueType;
import com.voipadmin.service.ConfigurationProcessingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Comparator;

import static java.util.Objects.nonNull;

@Service("yealink")
public class YealinkConfigurationProcessingStrategy implements ConfigurationProcessingStrategy {

    private static final String SECTION_TITLE_TEMPLATE =
        "#######################################################################################\r\n" +
        "##{0}{1}{2}##\r\n" +
        "#######################################################################################\r\n";
    private static final int SECTION_TITLE_LINE_LENGTH = 83;

    private StringBuilder templateBuilder;

    @Override
    public String buildConfig(Device device) {
        this.templateBuilder = new StringBuilder();
        this.templateBuilder.append("#!version:1.0.0.1").append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        processGeneralSettings(device);
        processLineSettings(device);
        processAdditionalSettings(device);

        return this.templateBuilder.toString();
    }

    @Override
    public void processGeneralSettings(Device device) {
        this.addSectionTitle("General Settings");
        addConfigValueOrEmptyString("static.network.internet_port.type", device.isDhcpEnabled() ? 0 : 2);
        if (!device.isDhcpEnabled()) {
            addConfigValueOrEmptyString("static.network.ipv6_internet_port.ip", device.getIpAddress());
            addConfigValueOrEmptyString("static.network.internet_port.mask", device.getSubnetMask());
            addConfigValueOrEmptyString("static.network.internet_port.gateway", device.getDefaultGw());
            addConfigValueOrEmptyString("static.network.primary_dns", device.getDns1());
            addConfigValueOrEmptyString("static.network.secondary_dns", device.getDns2());
        } else {
            addConfigValueOrEmptyString("static.network.dhcp_host_name", device.getHostname());
        }
        addConfigValueOrEmptyString("local_time.ntp_server1", device.getNtpServer());
        addConfigValueOrEmptyString(
            "static.auto_provision.server.url",
            device.getProvisioningMode().toString().toLowerCase(),
            "://",
            device.getProvisioningUrl()
        );
        addConfigValueOrEmptyString("static.security.user_name.admin", device.getWebLogin());
        addConfigValueOrEmptyString("static.security.user_password", device.getWebPassword());
    }

    public void processLineSettings(Device device) {
        device.getVoipAccounts().stream().sorted(Comparator.comparing(VoipAccount::getLineNumber))
            .forEach(voipAccount -> {
                this.addSectionTitle("Line Settings for Account " + voipAccount.getLineNumber());
                addConfigValueOrEmptyString("account.1.enable", voipAccount.isLineEnable() ? 1 : 0);
                addConfigValueOrEmptyString("account.1.label", voipAccount.getUsername());
                addConfigValueOrEmptyString("account.1.display_name", voipAccount.getUsername());
                addConfigValueOrEmptyString("account.1.auth_name", voipAccount.getUsername());
                addConfigValueOrEmptyString("account.1.user_name", voipAccount.getUsername());
                addConfigValueOrEmptyString("account.1.password", voipAccount.getPassword());
                addConfigValueOrEmptyString("account.1.sip_server.1.address", voipAccount.getSipServer());
                addConfigValueOrEmptyString("account.1.sip_server.1.port", voipAccount.getSipPort());
            });
    }

    @Override
    public void processAdditionalSettings(Device device) {
        this.addSectionTitle("Additional Settings");
        device.getSettings().forEach(setting -> {
            if (setting.getOption().getValueType().equals(OptionValueType.SELECT)) {
                setting.getSelectedValues().forEach(value ->
                    addConfigValueOrEmptyString(setting.getOption().getCode(), value.getValue())
                );
            } else {
                addConfigValueOrEmptyString(setting.getOption().getCode(), setting.getTextValue());
            }
        });
    }

    private void addSectionTitle(String title) {
        this.templateBuilder.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        this.templateBuilder.append(MessageFormat.format(
            SECTION_TITLE_TEMPLATE,
            StringUtils.repeat(" ", (SECTION_TITLE_LINE_LENGTH - title.length())/2),
            title,
            StringUtils.repeat(" ", title.length() % 2 != 0 ?
                (SECTION_TITLE_LINE_LENGTH - title.length())/2 : (SECTION_TITLE_LINE_LENGTH - title.length())/2 + 1)
        ));
        this.templateBuilder.append(LINE_SEPARATOR);
    }

    private void addConfigValueOrEmptyString(String option, Object... values) {
        this.templateBuilder.append(option).append(" = ");
        for (Object value : values) {
            this.templateBuilder.append(nonNull(value) ? value : "");
        }
        this.templateBuilder.append(LINE_SEPARATOR);
    }
}
