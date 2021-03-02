package com.voipadmin.service.impl;

import com.voipadmin.service.ConfigurationBuilderService;
import com.voipadmin.service.DeviceConfigurationWriterService;
import com.voipadmin.service.DeviceService;
import com.voipadmin.domain.Device;
import com.voipadmin.repository.DeviceRepository;
import com.voipadmin.service.dto.DeviceDTO;
import com.voipadmin.service.mapper.DeviceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    private final ConfigurationBuilderService configurationBuilderService;
    private final DeviceConfigurationWriterService deviceConfigurationWriterService;

    public DeviceServiceImpl(
        DeviceRepository deviceRepository,
        DeviceMapper deviceMapper,
        ConfigurationBuilderService configurationBuilderService,
        DeviceConfigurationWriterService deviceConfigurationWriterService
    ) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
        this.configurationBuilderService = configurationBuilderService;
        this.deviceConfigurationWriterService = deviceConfigurationWriterService;
    }

    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save Device : {}", deviceDTO);
        Device device = deviceMapper.toEntity(deviceDTO);
        device.setMac(device.getMac().toLowerCase());
        device = deviceRepository.save(device);
        deviceConfigurationWriterService.writeConfig(
            configurationBuilderService.buildConfig(device),
            configurationBuilderService.getFileName(device),
            device.getProvisioningMode()
        );
        return deviceMapper.toDto(device);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable)
            .map(deviceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceDTO> findOne(Long id) {
        log.debug("Request to get Device : {}", id);
        return deviceRepository.findById(id)
            .map(deviceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device : {}", id);
        deviceRepository.deleteById(id);
    }

    @Override
    public String getConfig(String mac) {
        Optional<Device> optionalDevice = findByMac(mac);
        return optionalDevice.map(configurationBuilderService::buildConfig).orElse(null);
    }

    @Override
    public Optional<Device> findByMac(String mac) {
        return deviceRepository.findByMacEquals(mac);
    }


}
