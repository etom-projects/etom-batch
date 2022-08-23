package com.kurly.delivery.batch.batch.domain.user.repository;

import com.kurly.delivery.batch.batch.domain.user.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {

    @Query(value = "from JobType jobType " +
            "where jobType.id = 1")
    JobType findFirstTimeJob();

    @Query(value = "from JobType jobType " +
            "where jobType.id = 2")
    JobType findSecondTimeJob();

    @Query(value = "from JobType jobType " +
            "where jobType.id = 3")
    JobType findThirdTimeJob();
}
