package com.kurly.delivery.batch.batch.domain.user.model;

import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.common.model.BaseTimeEntity;
import com.kurly.delivery.batch.batch.domain.user.enumerable.EJobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JOB_TYPES")
public class JobType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "JOB_TYPE")
    @Enumerated(value = EnumType.STRING)
    private EJobType type;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "FINISH_TIME")
    private LocalTime finishTime;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
