package com.databaseproject.photoplat.model;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "applyorder")
public class ApplyOrder {

    @Id
    private Integer order_id;

    private String username;

    private String apply_time;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public ApplyOrder() {
    }

    @Override
    public String toString() {
        return "ApplyOrder{" +
                "order_id=" + order_id +
                ", username='" + username + '\'' +
                ", apply_time='" + apply_time + '\'' +
                '}';
    }
}
