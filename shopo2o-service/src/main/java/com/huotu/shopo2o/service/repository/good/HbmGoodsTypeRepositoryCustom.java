package com.huotu.shopo2o.service.repository.good;

import com.huotu.shopo2o.service.entity.good.HbmGoodsType;

import java.util.List;

/**
 * Created by luyuanyuan on 2017/10/10.
 */
public interface HbmGoodsTypeRepositoryCustom {

    List<HbmGoodsType> getLastUsedByStoreId(Long storeId);

    List<HbmGoodsType> getAllUsedByStoreId(Long storeId);
}
