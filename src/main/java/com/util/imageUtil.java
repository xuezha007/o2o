package com.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

public class imageUtil
{
//    产生缩略图
    public static  String generateThumbnail(CommonsMultipartFile thumbanil,String targetAddr){
        String realFileName=getRandomFileName();
//        获取扩展名
     String extension=getFileExtension(thumbanil);
     makeDirPath(targetAddr);
     String relativeAddr=targetAddr +realFileName+extension;
     File dest =new File(PathUtil.getImgBasePath()+relativeAddr);
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
