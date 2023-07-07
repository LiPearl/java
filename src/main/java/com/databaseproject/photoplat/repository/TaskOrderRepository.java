package com.databaseproject.photoplat.repository;

import com.databaseproject.photoplat.model.TaskOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskOrderRepository extends JpaRepository<TaskOrder, Integer> {

    @Query(value = "call getOrderByTask_id(?1)", nativeQuery = true)
    List<TaskOrder> getOrderByTask_id(int task_id);

    @Query(value = "call getComOrderByUsername(?1)", nativeQuery = true)
    List<TaskOrder> getComOrderByUsername(String username);

    @Query(value = "call getInComOrderByUsername(?1)", nativeQuery = true)
    List<TaskOrder> getInComOrderByUsername(String username);

    @Query(value = "call finishOrder(?1)", nativeQuery = true)
    Integer finishOrder(int order_id);

    @Query(value = "call addOrder(?1,?2)", nativeQuery = true)
    @Modifying
    void addOrder(String username,int task_id);

    @Query(value = "call delOrderByOrder_id(?1)", nativeQuery = true)
    @Modifying
    Integer delOrderByOrder_id(int order_id);

    @Query(value = "call acceptOrder(?1)", nativeQuery = true)
    Integer acceptOrder(int order_id);

    @Query(value = "call rejectOrder(?1)", nativeQuery = true)
    @Modifying
    Integer rejectOrder(int order_id);

}
