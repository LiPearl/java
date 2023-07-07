package com.databaseproject.photoplat.service;


import com.databaseproject.photoplat.model.ApplyOrder;

import java.util.List;

public interface ApplyOrderService {

    public List<ApplyOrder> getApplyOrder(String username);

    public List<ApplyOrder> getInComApplyOrderByUsername(String username);

    public List<ApplyOrder> getComApplyOrderByUsername(String username);

    public ApplyOrder getApplyOrderByOrder_id(int order_id);
}
