package com.web.shopadmin;

import com.dto.ShopExecution;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.enums.ShopStateEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.AreaService;
import com.service.ShopCategoryService;
import com.service.ShopService;
import com.util.CodeUtil;
import com.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;






    @RequestMapping(value = "getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        try {
            shopCategoryList = shopCategoryService.getsShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registShop(HttpServletRequest request) throws IOException {
        Map<String, Object> modelMap = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误验证码");
            return  modelMap;

        }
        //        1 接受并转换
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
//
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2 注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            // TODO: 2019/9/8
            owner.setUserId(1L);
            shop.setOwner(owner);
//                File shopImgFile=new File(PathUtil.getImgBasePath() + imageUtil.getRandomFileName());
//                shopImgFile.createNewFile();
//                inputStreanmToFile(shopImg.getInputStream(),shopImgFile);
            ShopExecution se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
//            3 返回结果


    }
    // commonfile to file 输出流 输出到文件
//    private static void inputStreanmToFile(InputStream ins, File file) throws IOException {
//        OutputStream os=null ;
//        try{
//            os=new FileOutputStream(file);
//            int bytesRead=0;
//            byte[] buffer=new byte[1024];
//            while ((bytesRead=ins.read(buffer))!=-1){
//                os.write(buffer,0,bytesRead);
//            }
//        }catch(Exception e){
//     throw new RuntimeException("调用inputStream产生异常"+e.getMessage()) ;}
//        finally {
//            if(os!=null){
//                os.close();
//            }
//            if(ins!=null){
//                ins.close();
//            }
//        }
//    }
}
