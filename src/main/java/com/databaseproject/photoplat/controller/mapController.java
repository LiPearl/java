package com.databaseproject.photoplat.controller;


import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.service.TaskinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class mapController {

    @Autowired
    TaskinfoService taskinfoService;

    @GetMapping(value = "/getArroundTaskByGPS")
    @ResponseBody
    public List<Taskinfo> getArroundTaskByGPS(HttpServletRequest request, Model model,
                                      @RequestParam("city_name") String cityname,
                                      @RequestParam("latitude") double latitude,
                                      @RequestParam("longitude") double longitude){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        List<Taskinfo> taskinfos=taskinfoService.getArroundTaskByGPS(user.getUsername(),cityname,latitude,longitude);
        System.out.println(user.getUsername()+" "+cityname+" "+latitude+" "+longitude);
        System.out.println(taskinfos.size());
        for(int i=0;i<taskinfos.size();i++)
        System.out.println(taskinfos.get(i).toString());
        return taskinfos;
    }

    /*
        返回某个订单下的
     */
    @GetMapping(value = "/arroundTask")
    public String arroundTask(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "arroundTask";
    }

    @GetMapping(value = "/map")
    public String map(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "map";
    }

}
