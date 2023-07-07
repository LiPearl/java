package com.databaseproject.photoplat.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taskorder")
public class TaskOrder {

    @Id
    @GeneratedValue
    private int order_id;

    private int task_id;

    private String lastload_time;

    private String finish_time;

    private Integer is_accept;

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

    public Integer getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(Integer is_accept) {
        this.is_accept = is_accept;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }


    public TaskOrder() {
    }

    @Override
    public String toString() {
        return "TaskOrder{" +
                "order_id=" + order_id +
                ", task_id=" + task_id +
                ", lastload_time='" + lastload_time + '\'' +
                ", finish_time='" + finish_time + '\'' +
                '}';
    }
}
