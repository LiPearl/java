package com.databaseproject.photoplat.util;

import com.databaseproject.photoplat.model.ApplyOrder;
import com.databaseproject.photoplat.model.TaskOrder;
import com.databaseproject.photoplat.model.Taskinfo;

public class taskOrderWithapply_time {

    private int order_id;

    private int task_id;

    private String lastload_time;

    private String finish_time;

    private String apply_time;

    private String task_name;

    private String end_date;

    private int price;

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public taskOrderWithapply_time(TaskOrder taskOrder, ApplyOrder applyOrder, Taskinfo taskinfo) {
        this.order_id = taskOrder.getOrder_id();
        this.task_id = taskOrder.getTask_id();
        this.lastload_time = taskOrder.getLastload_time();
        this.finish_time = taskOrder.getFinish_time();
        this.apply_time = applyOrder.getApply_time();
        this.task_name=taskinfo.getTask_name();
        this.end_date=taskinfo.getEnd_date();
        this.price=taskinfo.getPrice();
    }

    @Override
    public String toString() {
        return "taskOrderWithapply_time{" +
                "order_id=" + order_id +
                ", task_id=" + task_id +
                ", lastload_time='" + lastload_time + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", apply_time='" + apply_time + '\'' +
                '}';
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getLastload_time() {
        return lastload_time;
    }

    public void setLastload_time(String lastload_time) {
        this.lastload_time = lastload_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
}
