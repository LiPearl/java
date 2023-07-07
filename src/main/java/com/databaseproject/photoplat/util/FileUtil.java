package com.databaseproject.photoplat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;

public class FileUtil {

    public static void copy(String toFile,String fromFile) throws Exception{
        File f1=new File(toFile);
        File f2=new File(fromFile);
        FileInputStream ins = new FileInputStream(f2);
        FileOutputStream out = new FileOutputStream(f1);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }
        ins.close();
        out.close();
    }

    public static boolean ifPicture(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            String extension = fileName.substring(i + 1);
            if (extension.equals("jpg") || extension.equals("png"))
                return true;
        } else
            return false;
        return false;
    }

    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            String extension = fileName.substring(i + 1);
            return extension;
        }
        return "";
    }
}
