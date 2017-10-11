package com.guaju.sugertea.ui.shopdetail;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

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

    }

    interface presenter{
        //获取商户详情的逻辑
        void  getShopDetail(String shanghuid);
    }

}
