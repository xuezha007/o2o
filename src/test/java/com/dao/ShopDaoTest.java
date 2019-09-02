package com.dao;

import ceshi.BaseTest;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest
{
    @Autowired
    private ShopDao shopDao;
    @Test
    public void  testInsertShop() {
        Shop shop= new Shop();
        PersonInfo owner=new PersonInfo();
        Area area =new Area();
        ShopCategory shopCategory=new ShopCategory();
        owner.setUserId(1l);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectnum=shopDao.insertShop(shop);
        assertEquals(1,effectnum);

    }
    @Test
    public void testUpdateShop(){
        Shop shop= new Shop();
        shop.setShopId(1L);

        shop.setShopAddr("测试的店铺gg");
        shop.setShopDesc("test2");

        int effectnum=shopDao.updateShop(shop);
        assertEquals(1,effectnum);
    }
}
