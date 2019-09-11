package com.service;

import com.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> getsShopCategoryList(ShopCategory shopCategoryCondition);
}
