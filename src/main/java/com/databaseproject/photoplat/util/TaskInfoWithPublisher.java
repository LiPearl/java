package com.databaseproject.photoplat.util;

import com.databaseproject.photoplat.model.TaskPublish;
import com.databaseproject.photoplat.model.Taskinfo;

import javax.persistence.Id;

public class TaskInfoWithPublisher {

    private Integer task_id;

    private String publisher_name;

    private String publish_time;

    private Integer applies;

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

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public Integer getApplies() {
        return applies;
    }

    public void setApplies(Integer applies) {
        this.applies = applies;
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
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

    @Override
    public String toString() {
        return "TaskInfoWithPublisher{" +
                "task_id=" + task_id +
                ", publisher_name='" + publisher_name + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", applies=" + applies +
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

    public TaskInfoWithPublisher(Taskinfo taskinfo, TaskPublish taskPublish) {
        this.task_id = taskinfo.getTask_id();
        this.publisher_name = taskPublish.getPublisher_name();
        this.publish_time = taskPublish.getPublish_time();
        this.applies = taskPublish.getApplies();
        this.task_name = taskinfo.getTask_name();
        this.city_name = taskinfo.getCity_name();
        this.latitude = taskinfo.getLatitude();
        this.longitude = taskinfo.getLongitude();
        this.price = taskinfo.getPrice();
        this.start_date = taskinfo.getStart_date();
        this.end_date = taskinfo.getEnd_date();
        this.introduction = taskinfo.getIntroduction();
        this.applies_limit = taskinfo.getApplies_limit();
    }
}
