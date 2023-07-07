package com.databaseproject.photoplat.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;


@Entity
@Table(name = "user")
public class User {

    /*@Id
    private Integer id;*/

    @Id
    //@Length(min = 5, max = 10, message = "用户名长度必须在5-10之间")
    private String username;

    @Length(min = 6, max = 10, message = "密码长度必须在6-10之间")
    private String password;

    private Integer credit;

    private Integer wallet;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getWallet() {
        return wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", credit=" + credit +
                ", wallet=" + wallet +
                '}';
    }
}
