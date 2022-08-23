package com.kurly.delivery.batch.batch.job;

import com.kurly.delivery.batch.batch.domain.address.model.Address;
import com.kurly.delivery.batch.batch.domain.address.repository.AddressRepository;
import com.kurly.delivery.batch.batch.domain.common.enumerable.Status;
import com.kurly.delivery.batch.batch.domain.customer.model.Customer;
import com.kurly.delivery.batch.batch.domain.customer.repository.CustomerRepository;
import com.kurly.delivery.batch.batch.domain.order.model.Order;
import com.kurly.delivery.batch.batch.domain.order.repository.OrderRepository;
import com.kurly.delivery.batch.batch.domain.product.model.Product;
import com.kurly.delivery.batch.batch.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Configuration
@RequiredArgsConstructor
public class RandomOrderJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JpaTransactionManager jpaTransactionManager;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Bean
    public Job randomOrderCreateJob() {
        return jobBuilderFactory.get("randomOrderCreateJob")
                .start(randomOrderCreateStep())
                .build();
    }

    @Bean
    public Step randomOrderCreateStep() {
        return stepBuilderFactory.get("randomOrderCreateStep")
                .tasklet((stepContribution, chunkContext) -> {
                    Random rand = new Random();
                    List<Product> products = productRepository.findAll();
                    List<Customer> customers = customerRepository.findAll();
                    List<Address> addresses = addressRepository.findAll();
                    List<Order> orders = new ArrayList<>();

                    for (int i = 0; i <= 100; i++) {
                        Product randomProduct = products.get(rand.nextInt(products.size()));
                        Customer randomCustomer = customers.get(rand.nextInt(customers.size()));
                        Address randomAddress = addresses.get(rand.nextInt(addresses.size()));

                        Order order = Order.builder()
                                .customer(randomCustomer)
                                .product(randomProduct)
                                .address(randomAddress)
                                .status(Status.ACTIVE)
                                .isCompleted(false)
                                .build();

                        orders.add(order);
                    }

                    orderRepository.saveAll(orders);

                    return FINISHED;
                })
                .transactionManager(jpaTransactionManager)
                .build();
    }
}
