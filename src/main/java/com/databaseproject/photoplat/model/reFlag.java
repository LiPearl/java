package com.databaseproject.photoplat.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reflag")
public class reFlag {

    @Id
    Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "reFlag{" +
                "flag=" + flag +
                '}';
    }
}
