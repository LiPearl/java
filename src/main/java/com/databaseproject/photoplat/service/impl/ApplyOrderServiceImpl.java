package com.databaseproject.photoplat.service.impl;


import com.databaseproject.photoplat.model.ApplyOrder;
import com.databaseproject.photoplat.repository.ApplyOrderRepository;
import com.databaseproject.photoplat.service.ApplyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplyOrderServiceImpl implements ApplyOrderService{

    @Autowired
    ApplyOrderRepository applyOrderRepository;

    @Override
    public List<ApplyOrder> getApplyOrder(String username) {
        return applyOrderRepository.getApplyOrder(username);
    }

    @Override
    public List<ApplyOrder> getInComApplyOrderByUsername(String username) {
        return applyOrderRepository.getInComApplyOrderByUsername(username);
    }

    @Override
    public List<ApplyOrder> getComApplyOrderByUsername(String username) {
        return applyOrderRepository.getComApplyOrderByUsername(username);
    }

    @Override
    public ApplyOrder getApplyOrderByOrder_id(int order_id) {
        return applyOrderRepository.getApplyOrderByOrder_id(order_id);
    }
}
