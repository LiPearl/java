package com.databaseproject.photoplat.service;

import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.model.publisher;

import org.springframework.util.StopWatch;

public interface UserService {

    User selectByUsername(String username);

    void insertUser(String username,String password);

    // 用于将输入的密码转为password函数下的密码
    String getPassword(String password);

    void updatePro(String username,String pro);

    Integer getPublisherId(String username);

    Integer addPublisher(String username);

    User getUser(String username);

    void updatePsd(String username,String password);

    void login(String username);

    public void addMoney(String username,int money);

    public byte[] getPortraitByUsername(String username);
    
    public void updatePortrait(String username, byte[] file);
}
