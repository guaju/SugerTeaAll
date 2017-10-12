package com.guaju.sugertea.ui.shopdetail;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.guaju.sugertea.model.bean.ShanghuUpDetailsBean;

/**
 * Created by guaju on 2017/10/11.
 */

public interface ShopDetailContract {
    interface  View{
        //失败
        //为空
        //提示网络异常
        //成功
        //头部信息渐变效果
        //设置tablayout+viewpager
        void bindTab2Vp(TabLayout tab, ViewPager viewPager);
        //显示布局上方信息
        void  setUpView(ShanghuUpDetailsBean bean);


    }

    interface presenter{
        //获取商户详情的逻辑
        void  getShopDetail(String shanghuid);
        //获得商户详情上方逻辑
        void  getShopUpDetail(String shanghuid);
        //获得商户详情下方数据
        void  getShopBelowDetail(String shanghuid);


    }

}
