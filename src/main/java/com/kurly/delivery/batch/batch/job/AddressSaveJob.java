package com.kurly.delivery.batch.batch.job;


import com.kurly.delivery.batch.batch.domain.address.model.Address;
import com.kurly.delivery.batch.batch.domain.address.model.AddressGroup;
import com.kurly.delivery.batch.batch.domain.address.repository.AddressRepository;
import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.customer.model.Customer;
import com.kurly.delivery.batch.batch.domain.delivery.repository.DeliveryRepository;
import com.kurly.delivery.batch.batch.domain.order.repository.OrderRepository;
import com.kurly.delivery.batch.batch.domain.user.repository.JobTypeRepository;
import com.kurly.delivery.batch.batch.domain.utils.CSVUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AddressSaveJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CSVUtils csvUtils;
    private final JpaTransactionManager jpaTransactionManager;
    private final AddressRepository addressRepository;
    private final DeliveryRepository deliveryRepository;

    @Bean
    public Job csvSaveJob() {
        return jobBuilderFactory.get("csvSaveJob")
                .start(csvSaveStep())
                .build();
    }

    @Bean
    public Step csvSaveStep() {
        return stepBuilderFactory.get("csvSaveStep")
                .transactionManager(jpaTransactionManager)
                .tasklet(csvSaveTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet csvSaveTasklet() {
        return (contribution, chunkContext) -> {
            List<Address> sampleAddress = new ArrayList<>();
            List<List<String>> objects = csvUtils.readCSV("/Users/motemote/Desktop/사이드프로젝트/batch/src/main/resources/static/label_xy.csv");
            Random random = new Random();
            for (List<String>object : objects) {
                try {
                    Long randomCustomerId = random.nextLong(1, 4);
                    Long randomZip = random.nextLong(100, 200);
                    Long randomZip2 = random.nextLong(100, 200);
                    Long groupId = Long.parseLong(object.get(1));
                    String latitude = object.get(2);
                    String longitude = object.get(3);

                    Address address = Address.builder()
                            .addressGroup(AddressGroup
                                    .builder()
                                    .id(groupId)
                                    .build())
                            .customer(Customer.builder()
                                    .id(randomCustomerId)
                                    .build())
                            .latitude(latitude)
                            .longitude(longitude)
                            .state("OO구 OO로 OO아파트")
                            .zipCode(randomZip + "동 " + randomZip2 + "호")
                            .status(Status.ACTIVE)
                            .build();

                    sampleAddress.add(address);
                } catch (NumberFormatException ex) {
                } catch (ArrayIndexOutOfBoundsException ex){

                }
            }

            addressRepository.saveAll(sampleAddress);
            return RepeatStatus.FINISHED;
            };
    }
}
