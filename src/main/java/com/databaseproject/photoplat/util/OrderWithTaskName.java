package com.databaseproject.photoplat.util;

import com.databaseproject.photoplat.model.ApplyOrder;
import com.databaseproject.photoplat.model.TaskOrder;
import com.databaseproject.photoplat.model.Taskinfo;

public class OrderWithTaskName {

    private int order_id;

    private String username;

    private Integer task_id;

    private String apply_time;

    private String lastload_time;

    private String finish_time;

    private String task_name;

    private String city_name;

    private double latitude;

    private double longitude;

    private Integer price;

    private String start_date;

    private String end_date;

    private String introduction;

    private int applies_limit;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
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

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public OrderWithTaskName(TaskOrder taskOrder, String task_name) {
        this.order_id = taskOrder.getOrder_id();
        this.task_id = taskOrder.getTask_id();
        this.lastload_time = taskOrder.getLastload_time();
        this.finish_time = taskOrder.getFinish_time();
        this.task_name = task_name;
    }

    public OrderWithTaskName(TaskOrder taskOrder, Taskinfo taskinfo, ApplyOrder applyOrder) {
        this.order_id = taskOrder.getOrder_id();
        this.task_id = taskOrder.getTask_id();
        this.lastload_time = taskOrder.getLastload_time();
        this.finish_time = taskOrder.getFinish_time();
        this.applies_limit=taskinfo.getApplies_limit();
        this.city_name=taskinfo.getCity_name();
        this.end_date=taskinfo.getEnd_date();
        this.introduction=taskinfo.getIntroduction();
        this.latitude=taskinfo.getLatitude();
        this.longitude=taskinfo.getLongitude();
        this.price=taskinfo.getPrice();
        this.start_date=taskinfo.getStart_date();
        this.task_name = taskinfo.getTask_name();
        this.apply_time=applyOrder.getApply_time();
        this.username=applyOrder.getUsername();
    }

    @Override
    public String toString() {
        return "OrderWithTaskName{" +
                "order_id=" + order_id +
                ", username='" + username + '\'' +
                ", task_id=" + task_id +
                ", apply_time='" + apply_time + '\'' +
                ", lastload_time='" + lastload_time + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", task_name='" + task_name + '\'' +
                '}';
    }
}
