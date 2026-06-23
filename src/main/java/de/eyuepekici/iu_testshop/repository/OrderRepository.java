package de.eyuepekici.iu_testshop.repository;

import de.eyuepekici.iu_testshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}