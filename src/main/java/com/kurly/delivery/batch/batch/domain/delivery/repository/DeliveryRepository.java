package com.kurly.delivery.batch.batch.domain.delivery.repository;

import com.kurly.delivery.batch.batch.domain.delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
