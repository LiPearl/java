package com.databaseproject.photoplat.service.impl;


import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.repository.TaskinfoRepository;
import com.databaseproject.photoplat.service.TaskinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@Transactional
public class TaskinfoServiceImpl implements TaskinfoService {

    static int taskid=0;

    @Autowired
    TaskinfoRepository taskRepository;

    @Override
    public Integer insertTask(String taskName, String publishername, String cityname, double latitude, double longitude,
                             int price, String startdata, String enddata, String introduction, int applieslimit) {
        /*String photopathment="//platAlldata//"+taskid;
        File dest = new File(photopathment);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }*/
        System.out.println(latitude+" "+longitude);
        return taskRepository.UpdateTask(taskName,publishername,cityname,latitude,longitude,price,startdata,enddata,introduction,applieslimit);
    }

    @Override
    public List<Taskinfo> getAllTaskInfoByPublisher_name(String publisher_name) {
        return taskRepository.getAllTaskInfoByPublisher_name(publisher_name);
    }

    @Override
    public Taskinfo getTask(int task_id) {
        return taskRepository.getTask(task_id);
    }

    @Override
    public Taskinfo getTaskinfobyOrder_id(int order_id) {
        return taskRepository.getTaskinfoByOrder_id(order_id);
    }

    @Override
    public List<Taskinfo> getArroundTaskByGPS(String username,String city_name,double latitude,double longitude) {
        return taskRepository.getArroundTaskByGPS(username,city_name,latitude,longitude);
    }

    @Override
    public Integer delPublish(int task_id) {
        return taskRepository.delPublish(task_id);
    }
}