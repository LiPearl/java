package com.databaseproject.photoplat.service;


import com.databaseproject.photoplat.model.TaskOrder;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface TaskOrderService {

    public List<TaskOrder> getOrderByTask_id(int task_id);

    public List<TaskOrder> getComOrderByUsername(String username);

    public List<TaskOrder> getInComOrderByUsername(String username);

    public Integer finishOrder(int order_id);

    public void addOrder(String username,int task_id);

    public Integer delOrderByOrder_id(int order_id);

    public Integer acceptOrder(int order_id);

    public Integer rejectOrder(int order_id);

}
