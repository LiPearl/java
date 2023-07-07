package com.databaseproject.photoplat.controller;


import com.databaseproject.photoplat.model.ApplyOrder;
import com.databaseproject.photoplat.model.TaskOrder;
import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.service.ApplyOrderService;
import com.databaseproject.photoplat.service.TaskOrderService;
import com.databaseproject.photoplat.service.TaskinfoService;
import com.databaseproject.photoplat.service.UserService;
import com.databaseproject.photoplat.util.OrderWithTaskName;
import com.databaseproject.photoplat.util.taskOrderWithapply_time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    TaskinfoService taskinfoService;

    @Autowired
    TaskOrderService taskOrderService;

    @Autowired
    ApplyOrderService applyOrderService;

    @GetMapping(value = "/getOrderInfo")
    public String getOrderInfo(HttpServletRequest request, Model model, @RequestParam("task_id") int task_id){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskinfoService.getTask(task_id);
        model.addAttribute("taskinfo",taskinfo);
        model.addAttribute("user",user);
        return "TaskOrder";
    }

    /*
        返回用户已完成订单的页面
     */
    @GetMapping(value = "/comOrder")
    public String comOrder(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "comOrder";
    }

    /*
        返回用户已完成订单的taskOrder数据
     */
    @GetMapping(value = "/getComOrderInfo")
    @ResponseBody
    public List<taskOrderWithapply_time> getComOrderInfo(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        List<TaskOrder> taskOrders=taskOrderService.getComOrderByUsername(user.getUsername());
        List<ApplyOrder> applyOrders=applyOrderService.getComApplyOrderByUsername(user.getUsername());
        List<taskOrderWithapply_time> taskOrderWithapply_times=new ArrayList<taskOrderWithapply_time>();
        for(int i=0;i<taskOrders.size();i++){
            taskOrderWithapply_times.add(new taskOrderWithapply_time(taskOrders.get(i),applyOrders.get(i),
                    taskinfoService.getTask(taskOrders.get(i).getTask_id())));
        }
        model.addAttribute("user",user);
        return taskOrderWithapply_times;
    }

    /*
        返回用户已完成订单的taskInfo数据
     */
    @GetMapping(value = "/getTaskname")
    @ResponseBody
    public Taskinfo getComOrderInfo(HttpServletRequest request, Model model, @RequestParam("task_id") int task_id){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskinfoService.getTask(task_id);
        return taskinfo;
    }

    /*
        返回商户已发布的某个订单 用户接单数据
    */
    @GetMapping(value = "/getTaskOrderdata")
    @ResponseBody
    public List<OrderWithTaskName> getTaskOrderdata(HttpServletRequest request, Model model, @RequestParam("task_id") int task_id){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        List<TaskOrder> taskOrders=taskOrderService.getOrderByTask_id(task_id);
        //List<ApplyOrder> applyOrders=applyOrderService.getComApplyOrderByUsername(user.getUsername());
        List<OrderWithTaskName> orderWithTaskNames=new ArrayList<OrderWithTaskName>();
        for(int i=0;i<taskOrders.size();i++){
            orderWithTaskNames.add(new OrderWithTaskName(taskOrders.get(i),
                            taskinfoService.getTask(taskOrders.get(i).getTask_id()),
                    applyOrderService.getApplyOrderByOrder_id(taskOrders.get(i).getOrder_id())
                    ));
        }
        return orderWithTaskNames;
    }

    /*
        用户未完成订单
     */
    @GetMapping(value = "/unOrder")
    public String unOrder(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "unOrder";
    }

    /*
        用户未完成订单数据
     */
    @GetMapping(value = "/unOrderInfo")
    @ResponseBody
    public List<taskOrderWithapply_time> unOrderInfo(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        List<TaskOrder> taskOrders=taskOrderService.getInComOrderByUsername(user.getUsername());
        List<ApplyOrder> applyOrders=applyOrderService.getInComApplyOrderByUsername(user.getUsername());
        List<taskOrderWithapply_time> taskOrderWithapply_times=new ArrayList<taskOrderWithapply_time>();
        for(int i=0;i<taskOrders.size();i++){
            taskOrderWithapply_times.add(new taskOrderWithapply_time(taskOrders.get(i),applyOrders.get(i),
                    taskinfoService.getTask(taskOrders.get(i).getTask_id())));
        }
        model.addAttribute("user",user);
        return taskOrderWithapply_times;
    }

    /*
        提交任务
     */
    @GetMapping(value = "/applyOrder")
    public String applyOrder(HttpServletRequest request, Model model,
                             @RequestParam("order_id") int order_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Integer flag = taskOrderService.finishOrder(order_id);
        if(flag==0)
            model.addAttribute("msg","   提交失败,请上传图片!");
        else
            model.addAttribute("msg","   提交成功");
        model.addAttribute("user",user);
        return "unOrder";
    }


    /*
        选择订单
     */
    @GetMapping(value = "/addOrder")
    public String addOrder(HttpServletRequest request, Model model,
                             @RequestParam("task_id") int task_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        taskOrderService.addOrder(user.getUsername(),task_id);
        model.addAttribute("user",user);
        return "unOrder";
    }

    @GetMapping(value ="/rejectOrder")
    public String rejectOrder(HttpServletRequest request, Model model,
                           @RequestParam("order_id") int order_id,@RequestParam("task_id") int task_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskinfoService.getTask(task_id);
        int flag=taskOrderService.rejectOrder(order_id);
        if(flag==0)
            model.addAttribute("msg","        删除失败!");
        else
            model.addAttribute("msg","        删除成功!");
        model.addAttribute("taskinfo",taskinfo);
        model.addAttribute("user",user);
        return "TaskOrder";
    }

    @GetMapping(value ="/delOrder")
    public String delOrder(HttpServletRequest request, Model model,
                              @RequestParam("order_id") int order_id,@RequestParam("task_id") int task_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskinfoService.getTask(task_id);
        int flag=taskOrderService.delOrderByOrder_id(order_id);
        if(flag==0)
            model.addAttribute("msg","        删除失败!");
        else
            model.addAttribute("msg","        删除成功!");
        model.addAttribute("taskinfo",taskinfo);
        model.addAttribute("user",user);
        return "comOrder";
    }

    @GetMapping(value ="/acceptOrder")
    public String accpetOrder(HttpServletRequest request, Model model,
                           @RequestParam("order_id") int order_id,@RequestParam("task_id") int task_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskinfoService.getTask(task_id);
        int flag=taskOrderService.acceptOrder(order_id);
        if(flag==0)
            model.addAttribute("msg","        完成失败!");
        else
            model.addAttribute("msg","       完成成功!");
        session.setAttribute("userSession", userService.getUser(user.getUsername()));
        session.setAttribute("userSessionId", user.getUsername());
        model.addAttribute("taskinfo",taskinfo);
        model.addAttribute("user",user);
        return "/TaskOrder";

    }
}
