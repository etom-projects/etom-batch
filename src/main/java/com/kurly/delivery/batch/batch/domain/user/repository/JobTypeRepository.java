package com.kurly.delivery.batch.batch.domain.user.repository;

import com.kurly.delivery.batch.batch.domain.user.model.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobTypeRepository extends JpaRepository<JobType, Long> {

    @Query(value = "from JobType jobType " +
            "where jobType.startTime = '00:00:00' and jobType.finishTime = '03:00:00'")
    JobType findFirstTimeJob();

    @Query(value = "from JobType jobType " +
            "where jobType.startTime = '02:00:00' and jobType.finishTime = '07:00:00'")
    JobType findSecondTimeJob();

    @Query(value = "from JobType jobType " +
            "where jobType.startTime = '04:00:00' and jobType.finishTime = '07:00:00'")
    JobType findThirdTimeJob();
}
