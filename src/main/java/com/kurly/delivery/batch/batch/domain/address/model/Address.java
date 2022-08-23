package com.kurly.delivery.batch.batch.domain.address.model;

import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.common.model.BaseTimeEntity;
import com.kurly.delivery.batch.batch.domain.customer.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESSES")
public class Address extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private AddressGroup addressGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    private String state;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    private String latitude;

    private String longitude;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
