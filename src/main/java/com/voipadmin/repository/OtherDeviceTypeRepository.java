package com.voipadmin.repository;

import com.voipadmin.domain.OtherDeviceType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OtherDeviceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtherDeviceTypeRepository extends JpaRepository<OtherDeviceType, Long> {
}
