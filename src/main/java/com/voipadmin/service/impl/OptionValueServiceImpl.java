package com.voipadmin.service.impl;

import com.voipadmin.service.OptionValueService;
import com.voipadmin.domain.OptionValue;
import com.voipadmin.repository.OptionValueRepository;
import com.voipadmin.service.dto.OptionValueDTO;
import com.voipadmin.service.mapper.OptionValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OptionValue}.
 */
@Service
@Transactional
public class OptionValueServiceImpl implements OptionValueService {

    private final Logger log = LoggerFactory.getLogger(OptionValueServiceImpl.class);

    private final OptionValueRepository optionValueRepository;

    private final OptionValueMapper optionValueMapper;

    public OptionValueServiceImpl(OptionValueRepository optionValueRepository, OptionValueMapper optionValueMapper) {
        this.optionValueRepository = optionValueRepository;
        this.optionValueMapper = optionValueMapper;
    }

    @Override
    public OptionValueDTO save(OptionValueDTO optionValueDTO) {
        log.debug("Request to save OptionValue : {}", optionValueDTO);
        OptionValue optionValue = optionValueMapper.toEntity(optionValueDTO);
        optionValue = optionValueRepository.save(optionValue);
        return optionValueMapper.toDto(optionValue);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptionValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionValues");
        return optionValueRepository.findAll(pageable)
            .map(optionValueMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OptionValueDTO> findOne(Long id) {
        log.debug("Request to get OptionValue : {}", id);
        return optionValueRepository.findById(id)
            .map(optionValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OptionValue : {}", id);
        optionValueRepository.deleteById(id);
    }
}
