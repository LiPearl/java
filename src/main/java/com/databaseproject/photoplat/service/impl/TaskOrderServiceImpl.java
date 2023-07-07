package com.databaseproject.photoplat.service.impl;


import com.databaseproject.photoplat.model.TaskOrder;
import com.databaseproject.photoplat.repository.TaskOrderRepository;
import com.databaseproject.photoplat.service.TaskOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskOrderServiceImpl implements TaskOrderService {

    @Autowired
    TaskOrderRepository taskOrderRepository;

    @Override
    public List<TaskOrder> getOrderByTask_id(int task_id) {
        return taskOrderRepository.getOrderByTask_id(task_id);
    }

    @Override
    public List<TaskOrder> getComOrderByUsername(String username) {
        return taskOrderRepository.getComOrderByUsername(username);
    }

    @Override
    public List<TaskOrder> getInComOrderByUsername(String username) {
        return taskOrderRepository.getInComOrderByUsername(username);
    }

    @Override
    public Integer finishOrder(int order_id) {
        return taskOrderRepository.finishOrder(order_id);
    }

    @Override
    public void addOrder(String username, int task_id) {
        taskOrderRepository.addOrder(username,task_id);
    }

    @Override
    public Integer delOrderByOrder_id(int order_id) {
        return taskOrderRepository.delOrderByOrder_id(order_id);
    }

    @Override
    public Integer acceptOrder(int order_id) {
        return taskOrderRepository.acceptOrder(order_id);
    }

    @Override
    public Integer rejectOrder(int order_id) {
        return taskOrderRepository.rejectOrder(order_id);
    }
}
