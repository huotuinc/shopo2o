/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 *
 */

package com.huotu.shopo2o.service.repository.good;
import com.huotu.shopo2o.service.entity.good.HbmGoodsSpecIndex;
import com.huotu.shopo2o.service.entity.good.HbmGoodsSpecIndexPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by luyuanyuan on 2017/10/11.
 */
@Repository
public interface HbmGoodsSpecIndexRepository extends JpaRepository<HbmGoodsSpecIndex,HbmGoodsSpecIndexPK>,JpaSpecificationExecutor {
    void deleteByGoodsId(int goodsId);

    void deleteByProductId(int productId);

    HbmGoodsSpecIndex findByProductIdAndSpecValueId(int productId, int specValueId);
}
