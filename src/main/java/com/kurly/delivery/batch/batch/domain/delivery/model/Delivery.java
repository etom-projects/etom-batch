package com.kurly.delivery.batch.batch.domain.delivery.model;

import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.common.model.BaseTimeEntity;
import com.kurly.delivery.batch.batch.domain.delivery.enumerable.DeliveryRank;
import com.kurly.delivery.batch.batch.domain.delivery.enumerable.DeliveryStatus;
import com.kurly.delivery.batch.batch.domain.order.model.Order;
import com.kurly.delivery.batch.batch.domain.user.model.JobType;
import com.kurly.delivery.batch.batch.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DELIVERIES")
public class Delivery extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_TYPE_ID")
    private JobType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_MAN_ID")
    private User deliveryMan;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "DELIVERY_STATUS")
    private DeliveryStatus deliveryStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "DELIVERY_RANK")
    private DeliveryRank deliveryRank;

    @Column(name = "DELIVERED_DATE")
    private LocalDate deliveredDate;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
