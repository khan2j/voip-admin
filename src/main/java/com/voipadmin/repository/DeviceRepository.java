package com.voipadmin.repository;

import com.voipadmin.domain.Device;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Device entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query
    Optional<Device> findByMacEquals(String mac);
}
