package com.databaseproject.photoplat.service;

import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.reFlag;

import java.util.List;

public interface TaskinfoService {

    public Integer insertTask(String taskName, String publishername, String cityname, double latitude, double longitude, int price, String startdata, String enddata, String introduction, int applieslimit);

    public List<Taskinfo> getAllTaskInfoByPublisher_name(String publisher_name);

    public Taskinfo getTask(int task_id);

    public Taskinfo getTaskinfobyOrder_id(int order_id);

    public List<Taskinfo> getArroundTaskByGPS(String username,String city_name,double latitude,double longitude);

    public Integer delPublish(int task_id);
}
