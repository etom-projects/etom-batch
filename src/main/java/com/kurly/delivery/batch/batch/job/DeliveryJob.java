package com.kurly.delivery.batch.batch.job;

import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.delivery.enumerable.DeliveryRank;
import com.kurly.delivery.batch.batch.domain.delivery.enumerable.DeliveryStatus;
import com.kurly.delivery.batch.batch.domain.delivery.model.Delivery;
import com.kurly.delivery.batch.batch.domain.delivery.repository.DeliveryRepository;
import com.kurly.delivery.batch.batch.domain.order.model.Order;
import com.kurly.delivery.batch.batch.domain.order.repository.OrderRepository;
import com.kurly.delivery.batch.batch.domain.user.model.JobType;
import com.kurly.delivery.batch.batch.domain.user.repository.JobTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeliveryJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OrderRepository orderRepository;
    private final JpaTransactionManager jpaTransactionManager;
    private final JobTypeRepository jobTypeRepository;
    private final DeliveryRepository deliveryRepository;

    @Bean
    public Job deliveriesCreateJob() {
        return jobBuilderFactory.get("deliveriesCreateJob")
                .start(deliveryCreateStep())
                .build();
    }

    @Bean
    public Step deliveryCreateStep() {
        return stepBuilderFactory.get("deliveriesCreateStep")
                .transactionManager(jpaTransactionManager)
                .tasklet(orderItemCreateTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet orderItemCreateTasklet() {
        return (contribution, chunkContext) -> {
            List<Delivery> firstDeliveries = new ArrayList<>();
            List<Delivery> secondDeliveries = new ArrayList<>();
            List<Delivery> thirdDeliveries = new ArrayList<>();
            LocalDateTime todayZeroTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0));
            List<Order> cRankOrderList = orderRepository.findCRankOrderList(todayZeroTime);
            List<Order> sortedOtherOrderList = orderRepository.findNotCRankOrderList(todayZeroTime);

            JobType firstTimeJob = jobTypeRepository.findFirstTimeJob();
            JobType secondTimeJob = jobTypeRepository.findSecondTimeJob();
            JobType thirdTimeJob = jobTypeRepository.findThirdTimeJob();

            for (Order cRankOrder : cRankOrderList) {
                Delivery delivery = Delivery
                        .builder()
                        .order(cRankOrder)
                        .type(firstTimeJob)
                        .deliveryStatus(DeliveryStatus.WAIT)
                        .deliveryRank(DeliveryRank.FreshnessToDeliveryRank(cRankOrder.getProduct().getFreshness()))
                        .status(Status.ACTIVE)
                        .build();

                firstDeliveries.add(delivery);
            }

            Integer half = sortedOtherOrderList.size() / 2;
            for (int i = 0; i < sortedOtherOrderList.size(); i++) {
                if (i < half) {
                    Order order = sortedOtherOrderList.get(i);
                    Delivery delivery = Delivery
                            .builder()
                            .order(order)
                            .type(secondTimeJob)
                            .deliveryStatus(DeliveryStatus.WAIT)
                            .deliveryRank(DeliveryRank.FreshnessToDeliveryRank(order.getProduct().getFreshness()))
                            .status(Status.ACTIVE)
                            .build();

                    secondDeliveries.add(delivery);
                } else {
                    Order order = sortedOtherOrderList.get(i);
                    Delivery delivery = Delivery
                            .builder()
                            .order(order)
                            .type(thirdTimeJob)
                            .deliveryStatus(DeliveryStatus.WAIT)
                            .deliveryRank(DeliveryRank.FreshnessToDeliveryRank(order.getProduct().getFreshness()))
                            .status(Status.ACTIVE)
                            .build();

                    thirdDeliveries.add(delivery);
                }
            }

            deliveryRepository.saveAll(firstDeliveries);
            deliveryRepository.saveAll(secondDeliveries);
            deliveryRepository.saveAll(thirdDeliveries);

            return RepeatStatus.FINISHED;
        };
    }
}
