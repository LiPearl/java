package com.databaseproject.photoplat.service.impl;

import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.repository.UserRepository;
import com.databaseproject.photoplat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User selectByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void insertUser(String username, String password) {
        userRepository.insertUser(username,password);
    }

    @Override
    public String getPassword(String password) {
        return userRepository.getPassword(password);
    }

    @Override
    public void updatePro(String username,String pro) {
        userRepository.UpdatePro(pro,username);
    }

    @Override
    public Integer getPublisherId(String username) {
        return userRepository.getPublisherId(username);
    }

    @Override
    public Integer addPublisher(String username) {
        return userRepository.addPublisher(username);
    }

    @Override
    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    @Override
    public void updatePsd(String username, String password) {
        userRepository.updatePsd(username,password);
    }

    @Override
    public void login(String username) {
        userRepository.login(username);
    }

    @Override
    public void addMoney(String username, int money) {
        userRepository.addMoney(username,money);
    }

    @Override
    public byte[] getPortraitByUsername(String username){
        return userRepository.getPortraitByUsername(username);
    }

    @Override
    public void updatePortrait(String username, byte[] file){
        userRepository.updatePortrait(username, file);
    }

}
