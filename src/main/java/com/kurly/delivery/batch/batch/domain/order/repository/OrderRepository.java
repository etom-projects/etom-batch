package com.kurly.delivery.batch.batch.domain.order.repository;

import com.kurly.delivery.batch.batch.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select productOrder " +
            "from Order productOrder " +
            "inner join productOrder.product " +
            "where productOrder.product.freshness >= 0  and productOrder.product.freshness <=3 " +
            " and productOrder.isCompleted = false " +
            " and productOrder.status = 'ACTIVE' and productOrder.createdAt > :now ")
    List<Order> findCRankOrderList(@Param("now") LocalDateTime now);

    @Query(value = "select productOrder " +
            "from Order productOrder " +
            "inner join productOrder.product " +
            "where productOrder.product.freshness >= 4 " +
            " and productOrder.isCompleted = false " +
            " and productOrder.status = 'ACTIVE' and productOrder.createdAt > :now " +
            " order by productOrder.product.freshness asc ")
    List<Order> findNotCRankOrderList(@Param("now") LocalDateTime now);
}
