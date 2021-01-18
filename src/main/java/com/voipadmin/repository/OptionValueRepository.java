package com.voipadmin.repository;

import com.voipadmin.domain.OptionValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OptionValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
}
