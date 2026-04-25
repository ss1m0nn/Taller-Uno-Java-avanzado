package com.taller.ecommerce.repository;

import com.taller.ecommerce.model.ItemOrden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {
    List<ItemOrden> findByOrdenId(Long ordenId);
}
