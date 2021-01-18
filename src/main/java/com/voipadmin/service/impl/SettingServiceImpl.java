package com.voipadmin.service.impl;

import com.voipadmin.service.SettingService;
import com.voipadmin.domain.Setting;
import com.voipadmin.repository.SettingRepository;
import com.voipadmin.service.dto.SettingDTO;
import com.voipadmin.service.mapper.SettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Setting}.
 */
@Service
@Transactional
public class SettingServiceImpl implements SettingService {

    private final Logger log = LoggerFactory.getLogger(SettingServiceImpl.class);

    private final SettingRepository settingRepository;

    private final SettingMapper settingMapper;

    public SettingServiceImpl(SettingRepository settingRepository, SettingMapper settingMapper) {
        this.settingRepository = settingRepository;
        this.settingMapper = settingMapper;
    }

    @Override
    public SettingDTO save(SettingDTO settingDTO) {
        log.debug("Request to save Setting : {}", settingDTO);
        Setting setting = settingMapper.toEntity(settingDTO);
        setting = settingRepository.save(setting);
        return settingMapper.toDto(setting);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Settings");
        return settingRepository.findAll(pageable)
            .map(settingMapper::toDto);
    }


    public Page<SettingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return settingRepository.findAllWithEagerRelationships(pageable).map(settingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SettingDTO> findOne(Long id) {
        log.debug("Request to get Setting : {}", id);
        return settingRepository.findOneWithEagerRelationships(id)
            .map(settingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Setting : {}", id);
        settingRepository.deleteById(id);
    }
}
