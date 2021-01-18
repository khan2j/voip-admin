package com.voipadmin.service.impl;

import com.voipadmin.service.VoipAccountService;
import com.voipadmin.domain.VoipAccount;
import com.voipadmin.repository.VoipAccountRepository;
import com.voipadmin.service.dto.VoipAccountDTO;
import com.voipadmin.service.mapper.VoipAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VoipAccount}.
 */
@Service
@Transactional
public class VoipAccountServiceImpl implements VoipAccountService {

    private final Logger log = LoggerFactory.getLogger(VoipAccountServiceImpl.class);

    private final VoipAccountRepository voipAccountRepository;

    private final VoipAccountMapper voipAccountMapper;

    public VoipAccountServiceImpl(VoipAccountRepository voipAccountRepository, VoipAccountMapper voipAccountMapper) {
        this.voipAccountRepository = voipAccountRepository;
        this.voipAccountMapper = voipAccountMapper;
    }

    @Override
    public VoipAccountDTO save(VoipAccountDTO voipAccountDTO) {
        log.debug("Request to save VoipAccount : {}", voipAccountDTO);
        VoipAccount voipAccount = voipAccountMapper.toEntity(voipAccountDTO);
        voipAccount = voipAccountRepository.save(voipAccount);
        return voipAccountMapper.toDto(voipAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VoipAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoipAccounts");
        return voipAccountRepository.findAll(pageable)
            .map(voipAccountMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VoipAccountDTO> findOne(Long id) {
        log.debug("Request to get VoipAccount : {}", id);
        return voipAccountRepository.findById(id)
            .map(voipAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VoipAccount : {}", id);
        voipAccountRepository.deleteById(id);
    }
}
