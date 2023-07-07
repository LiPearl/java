package com.databaseproject.photoplat.repository;


import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.rns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface rnsRepository extends JpaRepository<rns, Integer> {

    @Query(value = "call addRNS(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    @Modifying
    Integer addRNS(String username,String realname,String sex,String idnumber,String phonenum,String address);

    @Query(value = "call getRNS(?1)", nativeQuery = true)
    rns getRNS(String username);

}
