package com.databaseproject.photoplat.repository;

import com.databaseproject.photoplat.model.ApplyOrder;
import com.databaseproject.photoplat.model.rns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyOrderRepository extends JpaRepository<ApplyOrder, Integer> {


    @Query(value = "call getApplyOrder(?1)", nativeQuery = true)
    List<ApplyOrder> getApplyOrder(String username);


    @Query(value = "call getInComApplyOrderByUsername(?1)", nativeQuery = true)
    List<ApplyOrder> getInComApplyOrderByUsername(String username);

    @Query(value = "call getComApplyOrderByUsername(?1)", nativeQuery = true)
    List<ApplyOrder> getComApplyOrderByUsername(String username);


    @Query(value = "call getApplyOrderByOrder_id(?1)", nativeQuery = true)
    ApplyOrder getApplyOrderByOrder_id(int order_id);
}
