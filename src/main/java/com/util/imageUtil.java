package com.util;

import ch.qos.logback.classic.Logger;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class imageUtil
{
     private    static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();

     private static final SimpleDateFormat sDateFormat =new SimpleDateFormat("yyyyMMddHHmmss");
     private static Random r=new Random();
     private static Logger logger = (Logger) LoggerFactory.getLogger(imageUtil.class);

    public imageUtil() throws UnsupportedEncodingException {
    }

    //    产生缩略图
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) throws IOException {
        File newFile =new File(cFile.getOriginalFilename());
        cFile.transferTo(newFile);
        return newFile;
    }
    public static  String generateThumbnail(InputStream thumbanilInputStream, String fileName,String targetAddr) throws UnsupportedEncodingException {
        basePath=URLDecoder.decode(basePath,"utf-8");
        String realFileName=getRandomFileName();
//        获取扩展名
     String extension=getFileExtension(fileName);
     makeDirPath(targetAddr);
     String relativeAddr=targetAddr +realFileName+extension;
     logger.error("current relativeAddr is" + relativeAddr);

     File dest =new File(PathUtil.getImgBasePath()+relativeAddr);
     try{
         Thumbnails.of(thumbanilInputStream).size(200,200)
     .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
         .outputQuality(0.8f).toFile(dest);
     } catch (IOException e) {

         System.out.println("imageutil出问题了");
         logger.error(e.toString());
     }
        return relativeAddr;
    }

    private static void makeDirPath(String targetAddr) {
        String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;
        File dirPath =new File(realFileParentPath);
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }

    private static String getFileExtension(String fileName) {

        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static String getRandomFileName() {
        int rannum=r.nextInt(89999)+10000;
        String nowTimeStr=sDateFormat.format(new Date());
        return nowTimeStr+rannum;

    }

    public static void main(String[] args) throws IOException {
        String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        basePath= URLDecoder.decode(basePath,"utf-8");
        Thumbnails.of(new File("C:\\Users\\刘海邻\\Desktop\\QQ图片20190902090047.jpg"))
        .size(200,200)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/a.jpg" )),0.25f)
                .outputQuality(0.8f)
                .toFile("C:\\Users\\刘海邻\\Desktop\\xiaohuangren.jpg");
    }
}
