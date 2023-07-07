package com.databaseproject.photoplat.service.impl;

import com.databaseproject.photoplat.model.rns;
import com.databaseproject.photoplat.repository.rnsRepository;
import com.databaseproject.photoplat.service.rnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class rnsServiceImpl implements rnsService {

    @Autowired
    rnsRepository rnsrepository;

    @Override
    public void addRNS(String username, String realname, String sex, String idnumber, String phonenum, String address) {
        rnsrepository.addRNS(username,realname,sex,idnumber,phonenum,address);
    }

    @Override
    public rns getRNS(String username) {
        return rnsrepository.getRNS(username);
    }
}
