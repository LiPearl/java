package com.databaseproject.photoplat.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rns")
public class rns {

    @Id
    private String username;

    private String realname;

    private String sex;

    private String idnumber;

    private String phonenum;

    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public rns() {
        this.address="";
        this.idnumber="";
        this.phonenum="";
        this.realname="";
        this.sex="";
        this.username="";
    }

    @Override
    public String toString() {
        return "rns{" +
                "username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", sex='" + sex + '\'' +
                ", idnumber='" + idnumber + '\'' +
                ", phonenum='" + phonenum + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
