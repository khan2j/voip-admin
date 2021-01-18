package com.voipadmin.service.impl;

import com.voipadmin.service.OtherDeviceTypeService;
import com.voipadmin.domain.OtherDeviceType;
import com.voipadmin.repository.OtherDeviceTypeRepository;
import com.voipadmin.service.dto.OtherDeviceTypeDTO;
import com.voipadmin.service.mapper.OtherDeviceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OtherDeviceType}.
 */
@Service
@Transactional
public class OtherDeviceTypeServiceImpl implements OtherDeviceTypeService {

    private final Logger log = LoggerFactory.getLogger(OtherDeviceTypeServiceImpl.class);

    private final OtherDeviceTypeRepository otherDeviceTypeRepository;

    private final OtherDeviceTypeMapper otherDeviceTypeMapper;

    public OtherDeviceTypeServiceImpl(OtherDeviceTypeRepository otherDeviceTypeRepository, OtherDeviceTypeMapper otherDeviceTypeMapper) {
        this.otherDeviceTypeRepository = otherDeviceTypeRepository;
        this.otherDeviceTypeMapper = otherDeviceTypeMapper;
    }

    @Override
    public OtherDeviceTypeDTO save(OtherDeviceTypeDTO otherDeviceTypeDTO) {
        log.debug("Request to save OtherDeviceType : {}", otherDeviceTypeDTO);
        OtherDeviceType otherDeviceType = otherDeviceTypeMapper.toEntity(otherDeviceTypeDTO);
        otherDeviceType = otherDeviceTypeRepository.save(otherDeviceType);
        return otherDeviceTypeMapper.toDto(otherDeviceType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OtherDeviceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OtherDeviceTypes");
        return otherDeviceTypeRepository.findAll(pageable)
            .map(otherDeviceTypeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OtherDeviceTypeDTO> findOne(Long id) {
        log.debug("Request to get OtherDeviceType : {}", id);
        return otherDeviceTypeRepository.findById(id)
            .map(otherDeviceTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OtherDeviceType : {}", id);
        otherDeviceTypeRepository.deleteById(id);
    }
}
