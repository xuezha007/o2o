package com.dao;

import ceshi.BaseTest;
import com.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public  void testQueryShopCategory( ){
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryCategory(new ShopCategory());
        assertEquals(2,shopCategoryList.size());

    }
}
