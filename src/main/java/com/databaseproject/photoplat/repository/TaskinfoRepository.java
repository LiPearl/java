package com.databaseproject.photoplat.repository;


import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.reFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskinfoRepository extends JpaRepository<Taskinfo, Integer> {

    @Query(value = "call addTask(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
    Integer UpdateTask(String taskName, String publishername, String cityname, double latitude, double longitude, int price, String startdata, String enddata, String introduction, int applieslimit);

    @Query(value = "call getAllTaskInfoByPublisher_name(?1)", nativeQuery = true)
    List<Taskinfo> getAllTaskInfoByPublisher_name(String publisher_name);

    @Query(value = "call getTask(?1)", nativeQuery = true)
    Taskinfo getTask(int task_id);

    @Query(value = "call getTaskinfobyOrder_id(?1)", nativeQuery = true)
    Taskinfo getTaskinfoByOrder_id(int order_id);

    @Query(value = "call getArroundTaskByGPS(?1,?2,?3,?4)", nativeQuery = true)
    List<Taskinfo> getArroundTaskByGPS(String username,String city_name,double latitude,double longitude);

    @Query(value = "call delPublish(?1)", nativeQuery = true)
    Integer delPublish(int task_id);

}
