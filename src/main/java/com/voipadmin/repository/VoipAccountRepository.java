package com.voipadmin.repository;

import com.voipadmin.domain.VoipAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VoipAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoipAccountRepository extends JpaRepository<VoipAccount, Long> {
}
