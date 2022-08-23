package com.kurly.delivery.batch.batch.domain.user.model;


import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.common.model.BaseTimeEntity;
import com.kurly.delivery.batch.batch.domain.user.enumerable.ERole;
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
@Table(name = "USERS")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Column(name = "HASHED_PASSWORD")
    private String hashPassword;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private ERole role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_TYPE_ID")
    private JobType jobType;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    public void updateHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void updateJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public void updateRole(ERole role) {
        this.role = role;
    }
}
