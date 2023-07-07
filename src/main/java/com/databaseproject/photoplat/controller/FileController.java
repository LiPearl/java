package com.databaseproject.photoplat.controller;

import com.databaseproject.photoplat.model.TaskOrder;
import com.databaseproject.photoplat.model.Taskinfo;
import com.databaseproject.photoplat.model.User;
import com.databaseproject.photoplat.service.TaskOrderService;
import com.databaseproject.photoplat.service.TaskinfoService;
import com.databaseproject.photoplat.service.UserService;
import com.databaseproject.photoplat.util.imageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    UserService userService;

    @Autowired
    TaskOrderService taskOrderService;

    @Autowired
    TaskinfoService taskinfoService;

    void Myfilestream(InputStream fis, OutputStream fos){
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try{
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
        }catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    //文件下载相关代码
    @RequestMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response){
        String filename="背景.jpg";
        String filePath = "./src/main/resources/static/platAlldata" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    @PostMapping(value = "/updatePortrait")
    public String updatePortrait(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        // System.out.println(user.getUsername());
        // System.out.println("file".getBytes());

        try {
                userService.updatePortrait(user.getUsername(), file.getBytes());
                // imageUtil.download(0,12);
                request.setAttribute("msg", "更换成功");
                model.addAttribute("user",user);
                return "redirect:/index/changePro";

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/index/changePro";
    }

    /*
        展示头像
     */
    @GetMapping(value = "/showPortrait")
    public void showPortrait(HttpServletRequest request, HttpServletResponse response,Model model){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        byte[] portrait=userService.getPortraitByUsername(user.getUsername());
        if(portrait!=null){
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=portrait");
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                os.write(portrait);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ;
        }
        String filename="user04.png";
        String filePath = "./src/main/resources/static/secondStyledata/assets/img" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /*
        上传照片
     */
    @GetMapping(value = "/uploadPhoto")
    public String uploadPhoto(HttpServletRequest request, HttpServletResponse response,
                            Model model, @RequestParam("order_id") int order_id,
                            @RequestParam("username") String username){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        Taskinfo taskinfo=taskinfoService.getTaskinfobyOrder_id(order_id);
        model.addAttribute("task_name",taskinfo.getTask_name());
        model.addAttribute("user",user);
        model.addAttribute("order_id",order_id);
        return "upload";
    }

    /*
        将上传上来的照片写入数据库
     */
    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                         Model model, @RequestParam("order_id") int order_id){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        try {
            //  将图片文件传入数据库
            imageUtil.uploadPhoto(order_id,file);
            request.setAttribute("msg", "上传成功");
            model.addAttribute("user",user);
            return "redirect:/uploadPhoto?order_id="+order_id+"&username="+user.getUsername();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadPhoto?order_id="+order_id+"&username="+user.getUsername();
    }

    /*
        返回用户的某个订单的所有图片
     */
    @GetMapping(value = "/downloadPhoto")
    public String downloadPhoto(HttpServletRequest request, HttpServletResponse response,
                              Model model,@RequestParam("order_id") int order_id,
                              @RequestParam("username") String username){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        int num=imageUtil.getPhotoNumByOrder_id(order_id);
        Taskinfo taskinfo=taskinfoService.getTaskinfobyOrder_id(order_id);
        model.addAttribute("task_name",taskinfo.getTask_name());
        model.addAttribute("sumOfphoto",num);
        model.addAttribute("order_id",order_id);
        return "showOrderPhoto";
    }

    /*
        已完成订单下的图片
     */
    @GetMapping(value = "/downloadPhoto2")
    public String downloadPhoto2(HttpServletRequest request, HttpServletResponse response,
                                Model model,@RequestParam("order_id") int order_id,
                                @RequestParam("username") String username){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        int num=imageUtil.getPhotoNumByOrder_id(order_id);
        Taskinfo taskinfo=taskinfoService.getTaskinfobyOrder_id(order_id);
        model.addAttribute("task_name",taskinfo.getTask_name());
        model.addAttribute("sumOfphoto",num);
        model.addAttribute("order_id",order_id);
        return "showOrderPhoto2";
    }


    /*
        返回某个订单下所有的photoid
     */
    @GetMapping(value = "/getPhotoId")
    @ResponseBody
    public List<Integer> getPhotoId(HttpServletRequest request, HttpServletResponse response,
                                Model model,@RequestParam("order_id") int order_id){

        List<Integer> list=imageUtil.getPhotoId(order_id);
        return list;
    }

    /*
        根据photoid展示图片
     */
    @GetMapping(value = "/showPic")
    public void showPic(HttpServletRequest request, HttpServletResponse response,
                        Model model, @RequestParam("photo_id") int photo_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        byte[] portrait=imageUtil.getPhotoByphoto_id(photo_id);
        if(portrait!=null){
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=portrait");
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                os.write(portrait);
                os.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return ;
        }
    }
    /*
        根据photo_id下载图片
     */
    @GetMapping(value = "/downloadPic")
    public String downloadPic(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("photo_id") int photo_id){
            byte[] buffer=imageUtil.getPhotoByphoto_id(photo_id);
            OutputStream os = null; //输出流
            try {
                // 设置头部信息
                response.setContentType("application/force-download");
                response.setHeader("Content-Disposition", "attachment;fileName=pic"+photo_id+".jpg");
                // 将图片写入输出
                os = response.getOutputStream();
                os.write(buffer);
                os.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("抛异常!");
            }
        return null;
    }

    /*
        根据photo_id删除图片
     */
    @GetMapping(value = "/delPic")
    public String delPic(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("photo_id") int photo_id,@RequestParam("order_id") int order_id){

        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        imageUtil.delPhotoByPhoto_id(photo_id);
        return "redirect:/downloadPhoto?order_id="+order_id+"&username="+user.getUsername();
    }

}
