package com.databaseproject.photoplat.service.impl;


import com.databaseproject.photoplat.model.TaskPublish;
import com.databaseproject.photoplat.repository.TaskPublishRepository;
import com.databaseproject.photoplat.service.TaskPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskPublishServiceImpl implements TaskPublishService{

    @Autowired
    TaskPublishRepository taskPublishRepository;

    @Override
    public TaskPublish getTaskPublish(int task_id) {
        return taskPublishRepository.getTaskPublish(task_id);
    }
}
