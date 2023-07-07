package com.databaseproject.photoplat.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taskpublish")
public class TaskPublish {

    @Id
    private Integer task_id;

    private String publisher_name;

    private String publish_time;

    private Integer applies;

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

    public TaskPublish() {
    }

    @Override
    public String toString() {
        return "TaskPublish{" +
                "task_id=" + task_id +
                ", publisher_name='" + publisher_name + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", applies=" + applies +
                '}';
    }
}




