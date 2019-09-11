package com.dao;

import com.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> queryCategory(@Param("shopCategoryCondition")
                                     ShopCategory shopCategory);

}
