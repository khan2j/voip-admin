package com.voipadmin.repository;

import com.voipadmin.domain.AsteriskAccount;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AsteriskAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsteriskAccountRepository extends JpaRepository<AsteriskAccount, Long> {
}
