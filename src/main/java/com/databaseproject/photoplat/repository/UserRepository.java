package com.databaseproject.photoplat.repository;


import com.databaseproject.photoplat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query(value = "select password(?1);", nativeQuery = true)
    String getPassword(String password);

    //利用原生的SQL进行插入操作
    //  1、在@Query注解中编写JPQL实现DELETE和UPDATE操作的时候必须加上@modifying注解，以通知Spring Data 这是一个DELETE或UPDATE操作。
    //  2、UPDATE或者DELETE操作需要使用事务，此时需要 定义Service层，在Service层的方法上添加事务操作。
    //  3、注意JPQL不支持INSERT操作。　
    @Query(value = "call addUser(?1,?2);", nativeQuery = true)
    @Modifying
    void insertUser(String username,String password);

    @Query(value = "update user set portrait=?1 where username=?2", nativeQuery = true)
    @Modifying
    void UpdatePro(String pro,String username);

    @Query(value = "call getPublisher_name(?1)", nativeQuery = true)
    Integer getPublisherId(String username);

    @Query(value = "call addPublisher(?1)", nativeQuery = true)
    Integer addPublisher(String username);

    @Query(value = "call getUser(?1)", nativeQuery = true)
    User getUser(String username);

    @Query(value = "call updatePsd(?1,?2)", nativeQuery = true)
    @Modifying
    void updatePsd(String username,String password);

    @Query(value = "call login(?1)", nativeQuery = true)
    @Modifying
    void login(String username);

    @Query(value = "call addMoney(?1,?2)", nativeQuery = true)
    @Modifying
    void addMoney(String username,int money);

    @Query(value = "call getPortraitByUsername(?1)", nativeQuery = true)
    byte[] getPortraitByUsername(String username);

    @Query(value = "call updatePortrait(?1,?2)", nativeQuery =true)
    @Modifying
    void updatePortrait(String username, byte[] file);
}
