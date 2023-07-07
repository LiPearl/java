package com.databaseproject.photoplat.repository;

import com.databaseproject.photoplat.model.TaskPublish;
import com.databaseproject.photoplat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskPublishRepository extends JpaRepository<TaskPublish, Integer> {

    @Query(value = "call getTaskPublish(?1)", nativeQuery = true)
    TaskPublish getTaskPublish(int task_id);

}
