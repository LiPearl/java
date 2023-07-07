package com.databaseproject.photoplat.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "city")
public class City {

    @Id
    private String city_name;

    Integer task_num;

    Integer fin_order_num;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Integer getTask_num() {
        return task_num;
    }

    public void setTask_num(Integer task_num) {
        this.task_num = task_num;
    }

    public Integer getFin_order_num() {
        return fin_order_num;
    }

    public void setFin_order_num(Integer fin_order_num) {
        this.fin_order_num = fin_order_num;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "city_name='" + city_name + '\'' +
                ", task_num=" + task_num +
                ", fin_order_num=" + fin_order_num +
                '}';
    }
}
