package com.service;

import com.dto.ShopExecution;
import com.entity.Shop;

import java.io.InputStream;

public interface ShopService {
//    inputstream获得不了文件名字
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName);
}
