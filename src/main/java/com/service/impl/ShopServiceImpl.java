package com.service.impl;

import com.dao.ShopDao;
import com.dto.ShopExecution;
import com.entity.Shop;
import com.enums.ShopStateEnum;
import com.exceptions.ShopOperationException;
import com.service.ShopService;
import com.util.PathUtil;
import com.util.imageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息初始值
            shop.setEnableStatus(0); //0 未上架 审核中
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0) {
                throw new ShopOperationException("店铺创建失败");

            } else {
                if (shopImgInputStream != null) {
                    try {
                        addShopImg(shop, shopImgInputStream,fileName);


                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error" + e.getMessage());
                    }
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }


            }
        } catch (Exception e) {
            throw new ShopOperationException("ShopServiceImpl 出了问题 addShop error" + e.getMessage());

        }
        return new ShopExecution((ShopStateEnum.CHECK), shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) throws UnsupportedEncodingException {
        //获取shop图片目录相对路经
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = imageUtil.generateThumbnail(shopImgInputStream,fileName, dest);
        shop.setShopImg(shopImgAddr);
    }
}
