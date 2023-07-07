package com.databaseproject.photoplat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "taskinfo")
public class Taskinfo {

    @Id
    @GeneratedValue
    private Integer task_id;

    private String task_name;

    private String city_name;

    private double latitude;

    private double longitude;

    private int price;

    private String start_date;

    private String end_date;

    private String introduction;

    private int applies_limit;

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getApplies_limit() {
        return applies_limit;
    }

    public void setApplies_limit(int applies_limit) {
        this.applies_limit = applies_limit;
    }

    public Taskinfo() {
    }

    @Override
    public String toString() {
        return "Taskinfo{" +
                "task_id=" + task_id +
                ", task_name='" + task_name + '\'' +
                ", city_name='" + city_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", price=" + price +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", introduction='" + introduction + '\'' +
                ", applies_limit=" + applies_limit +
                '}';
    }
}
