package com.databaseproject.photoplat.util;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class imageUtil {

    public static void uplaodPortrait(String username,MultipartFile file){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call uploadPortrail(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            byte[] b=new byte[1024];
            InputStream in = file.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            while((len = in.read(b)) != -1){
                baos.write(b, 0, len);
            }
            ps.setString(1,username);
            ps.setBytes(2,baos.toByteArray());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void download(int type,int id){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call downloadImage(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Blob blob = rs.getBlob(1);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Lin\\Desktop\\out.jpg"));
                InputStream in = blob.getBinaryStream();
                byte[] b = new byte[1024];
                int len;
                while ((len = in.read(b)) != -1) {
                    bos.write(b, 0, len);
                    bos.flush();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] downloadPortrait(String username){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call getPortraitByUsername(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            byte[] b=new byte[1024];
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Blob blob = rs.getBlob(1);
                b=rs.getBytes(1);
                return b;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    /*
        上传照片
     */
    public static void uploadPhoto(int order_id,MultipartFile file){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call uploadPhoto(?,?)";   // 调用存储过程
            PreparedStatement ps = conn.prepareStatement(sql);
            byte[] b=new byte[1024];
            InputStream in = file.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            while((len = in.read(b)) != -1){
                baos.write(b, 0, len);  // 将文件以流的形式写入baos中
            }
            ps.setInt(1,order_id);
            ps.setBytes(2,baos.toByteArray());
            ps.executeUpdate(); // 执行存储过程
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        获取某个Order_id下的所有图片的数量
     */
    public static int getPhotoNumByOrder_id(int order_id){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call getPhotoNumByOrder_id(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,order_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int num = rs.getInt(1);
                return num;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return 0;
    }


    /*
        返回某个order_id下的第tim个图片
     */
    public static byte[] getPicByTim(int order_id,int tim){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call downloadPhotoByOrder_id(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            byte[] b=new byte[1024];
            ps.setInt(1,order_id);
            ResultSet rs = ps.executeQuery();
            int i=1;
            while(rs.next()) {
                b=rs.getBytes(1);
                if(i==tim+1)
                    return b;
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }


    /*
        返回某个order_id的photoid
     */
    public static List<Integer> getPhotoId(int order_id){
        try {
            List<Integer> list=new ArrayList<Integer>();
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call getPhoto_idByOrder_id(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,order_id);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int numberOfColumns = md.getColumnCount();
            while(rs.next())
            {
                for(int r=1;r<numberOfColumns+1;r++)
                {
                    list.add(rs.getInt(r));
                }
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }


    /*
        通过photo_id获取photo
     */
    public static byte[] getPhotoByphoto_id(int photo_id){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call downloadPhotoByPhoto_id(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            byte[] b=new byte[1024];
            ps.setInt(1,photo_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                b=rs.getBytes(1);
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    /*
        根据photo_id删除图片
     */
    public static void delPhotoByPhoto_id(int photo_id){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?useSSL=true","root","123456");
            String sql = "call delPhotoByPhoto_id(?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,photo_id);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
