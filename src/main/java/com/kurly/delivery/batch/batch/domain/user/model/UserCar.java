package com.kurly.delivery.batch.batch.domain.user.model;


import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.common.model.BaseTimeEntity;
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
@Table(name = "USER_CARS")
public class UserCar extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "LICENSE_PLATE")
    private String licensePlate;

    @Column(name = "LICENSE_PLATE_COLOR")
    private String licensePlateColor;

    private String model;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
