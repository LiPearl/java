package com.databaseproject.photoplat.controller;

import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.service.UserService;
import com.databaseproject.photoplat.util.FileUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, User user, Model model){
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        // user.setPassword(userService.getPassword(user.getPassword()));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),userService.getPassword(user.getPassword()));
        try {
            subject.login(token);
            userService.login(user.getUsername());
            return "redirect:/index";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    @GetMapping(value = "/index")
    public String index(HttpServletRequest request,Model model) {
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        //return  "mapexample";
        return "map";
    }

    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(HttpServletRequest request, User user, Model model){

        if(userService.selectByUsername(user.getUsername())!=null){
            request.setAttribute("msg","该用户名已存在!");
            return "register";
        }

        userService.insertUser(user.getUsername(), userService.getPassword(user.getPassword()));
        // 头像地址
        File portrait=new File("./src/main/resources/static/platAlldata/"+user.getUsername()+"port/port.jpg");
        if (!portrait.exists() && !portrait.getParentFile().exists()) {
            portrait.getParentFile().mkdirs();
        }
        String fromPath="./src/main/resources/static/firstStyledata/assets/i/f15.jpg";
        try {
            FileUtil.copy(portrait.getPath(),fromPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "login";
    }

    // 退出登录
    @GetMapping(value = "/loginout")
    public String loginout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
        return "login";
    }

    @GetMapping(value = "/download")
    public void download(HttpServletRequest request, Model model, HttpServletResponse httpServletResponse) {
        try {
            HttpSession session=request.getSession();
            User user=(User)session.getAttribute("userSession");
            model.addAttribute("user",user);

            File file=new File("./download/out.jpg");
            InputStream inputStream=new FileInputStream(file);
            httpServletResponse.setHeader("content-disposition", "attachment;filename="+file.getName());
            byte[] bytes=new byte[1024];
            int len;
            OutputStream outputStream=httpServletResponse.getOutputStream();
            while ((len = inputStream.read(bytes)) > 0) {
                //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
                    outputStream.write(bytes,0,len);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
