package com.databaseproject.photoplat.controller;


import com.databaseproject.photoplat.model.TaskPublish;
import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.service.TaskOrderService;
import com.databaseproject.photoplat.service.TaskPublishService;
import com.databaseproject.photoplat.service.TaskinfoService;
import com.databaseproject.photoplat.service.UserService;
import com.databaseproject.photoplat.util.TaskInfoWithPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TaskController {

    @Autowired
    TaskinfoService taskService;

    @Autowired
    UserService userService;

    @Autowired
    TaskOrderService taskOrderService;

    @Autowired
    TaskPublishService taskPublishService;

    @PostMapping(value = "/addTask")
    public String addTask(HttpServletRequest request, Model model,
                          @RequestParam("taskname") String taskname, @RequestParam("longitude") double longitude,
                          @RequestParam("latitude") double latitude, @RequestParam("limit") int limit,
                          @RequestParam("endTime") String endTime, @RequestParam("startTime") String startTime,
                          @RequestParam("city") String city, @RequestParam("introduction1") String introduction,
                          @RequestParam("price1") int price){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Integer flag=taskService.insertTask(taskname,user.getUsername(),city,latitude,longitude,price,startTime,endTime,introduction,limit);
        if(flag==0){
            model.addAttribute("msg","    任务发布失败");
            model.addAttribute("user",user);
            return "releaseMap";
        }
        session.setAttribute("userSession", userService.getUser(user.getUsername()));
        session.setAttribute("userSessionId", user.getUsername());
        model.addAttribute("msg","     任务发布成功");
        model.addAttribute("user",user);
        return "releaseMap";
    }

    @GetMapping(value = "/TaskInfo")
    public String TaskInfo(HttpServletRequest request, Model model, @RequestParam("task_id") int task_id){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskService.getTask(task_id);
        TaskPublish taskPublish=taskPublishService.getTaskPublish(task_id);
        TaskInfoWithPublisher taskInfoWithPublisher=new TaskInfoWithPublisher(taskinfo,taskPublish);
        model.addAttribute("taskinfo",taskInfoWithPublisher);
        model.addAttribute("user",user);
        return "TaskInfo";
    }

    @GetMapping(value = "/showTaskInfo")
    public String showTaskInfo(HttpServletRequest request, Model model, @RequestParam("task_id") int task_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskService.getTask(task_id);
        TaskPublish taskPublish=taskPublishService.getTaskPublish(task_id);
        TaskInfoWithPublisher taskInfoWithPublisher=new TaskInfoWithPublisher(taskinfo,taskPublish);
        model.addAttribute("taskinfo",taskInfoWithPublisher);
        model.addAttribute("user",user);
        return "applyOrderByMap";
    }


    @GetMapping(value = "/delPublish")
    public String delPublish(HttpServletRequest request, Model model, @RequestParam("task_id") int task_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        int flag=taskService.delPublish(task_id);
        if(flag==0)
            model.addAttribute("msg","    任务删除失败!未过期!");
        else
            model.addAttribute("msg","    任务删除成功!");

        model.addAttribute("user",user);
        return "hasTask";
    }

}
