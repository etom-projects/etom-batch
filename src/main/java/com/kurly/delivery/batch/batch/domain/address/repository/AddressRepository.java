package com.kurly.delivery.batch.batch.domain.address.repository;

import com.kurly.delivery.batch.batch.domain.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
