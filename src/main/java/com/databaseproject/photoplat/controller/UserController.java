package com.databaseproject.photoplat.controller;

import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.model.rns;
import com.databaseproject.photoplat.service.TaskinfoService;
import com.databaseproject.photoplat.service.UserService;
import com.databaseproject.photoplat.service.rnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/index")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TaskinfoService taskinfoService;

    @Autowired
    rnsService rnsservice;

    @GetMapping(value = "/userInfo")
    public String userInfo(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        model.addAttribute("credit",user.getCredit());
        model.addAttribute("wallet",(float)user.getWallet().intValue());
        return "userInfo";
    }

    @GetMapping(value = "/changePro")
    public String changePro(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "changePro";
    }

    @GetMapping(value = "/applyBussiness")
    public String applyBussiness(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "applyBussiness";
    }

    /*
        通过地图定位发布位置
     */
    @GetMapping(value = "/releaseTask")
    public String releaseTask(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "releaseMap";
    }

    /*
        定位成功后发布
     */
    @GetMapping(value = "/release")
    public String release(HttpServletRequest request, Model model,
                          @RequestParam("longitude") double longitude,@RequestParam("latitude") double latitude){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        model.addAttribute("longitude",longitude);
        model.addAttribute("latitude", latitude);
        return "releaseTask";
    }


    /*
        当前已发布任务
     */
    @GetMapping(value = "/hasTask")
    public String hasTask(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "hasTask";
    }

    @GetMapping(value = "/getTaskInfo")
    @ResponseBody
    public List<Taskinfo> getTaskInfo(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        List<Taskinfo> list=taskinfoService.getAllTaskInfoByPublisher_name(user.getUsername());
        return list;
    }

    /*
        同意条款 申请为商家
     */
    @GetMapping(value = "/agreeAndsubmit")
    public String agreeAndsubmit(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Integer flag=userService.addPublisher(user.getUsername());
        if(flag == 0){
            model.addAttribute("user",user);
            model.addAttribute("msg","申请失败!您本身已经为商家或者信誉值不够!");
            return "applyBussiness";
        }
        model.addAttribute("user",user);
        model.addAttribute("msg","申请成功!");
        return "applyBussiness";
    }

    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public User getUserInfo(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        return userService.getUser(user.getUsername());
    }

    /*
        改密码
     */
    @GetMapping(value = "/changePassword")
    public String changePassword(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "changePassword";
    }

    /*
        接受改密的post请求
     */
    @PostMapping(value = "/changePasswd")
    public String changePasswd(HttpServletRequest request, Model model,User user){
        HttpSession session=request.getSession();
        User user1=(User)session.getAttribute("userSession");
        userService.updatePsd(user.getUsername(),user.getPassword());
        model.addAttribute("msg","改密成功!");
        model.addAttribute("user",user1);
        return "changePassword";
    }

    @GetMapping(value = "/rns")
    public String rns(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        rns Rns=rnsservice.getRNS(user.getUsername());
        if(Rns==null) {
            Rns = new rns();
            model.addAttribute("rns",(rns)Rns);
            return "rns";
        }
        model.addAttribute("rns",(rns)Rns);
        return "hasRNS";
    }

    @PostMapping(value = "/rns")
    public String rnsPost(HttpServletRequest request, Model model, rns Rns){
        System.out.println(Rns.toString());
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        rnsservice.addRNS(Rns.getUsername(),Rns.getRealname(),
                Rns.getSex(),Rns.getIdnumber(),Rns.getPhonenum(),Rns.getAddress());
        session.setAttribute("userSession", userService.getUser(user.getUsername()));
        session.setAttribute("userSessionId", user.getUsername());
        return "redirect:/index/rns";
    }

    @GetMapping(value = "/addMoney")
    public String addMoney(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "addMoney";
    }

    @PostMapping(value = "/addMoney")
    public String addMoneyPost(HttpServletRequest request, Model model,@RequestParam("money") int money){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        userService.addMoney(user.getUsername(),money);
        session.setAttribute("userSession", userService.getUser(user.getUsername()));
        session.setAttribute("userSessionId", user.getUsername());
        model.addAttribute("user",user);
        return "addMoney";
    }

    @GetMapping(value = "/aboutPlat")
    public String aboutPlat(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        return "aboutPlat";
    }

}
