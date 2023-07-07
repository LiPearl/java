package com.databaseproject.photoplat.service;

import com.databaseproject.photoplat.model.rns;

public interface rnsService {

    public void addRNS(String username,String realname,String sex,String idnumber,String phonenum,String address);

    public rns getRNS(String username);

}
