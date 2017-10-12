package com.guaju.sugertea.ui.shopdetail;

import com.guaju.sugertea.httputil.HttpHelper;

/**
 * Created by guaju on 2017/10/11.
 */

public class ShopDetailPresenterImpl implements ShopDetailContract.presenter {
    ShopDetailContract.View view;
    ShopDetailActivity activity;

    public ShopDetailPresenterImpl(ShopDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getShopDetail(String shanghuid) {
        getShopUpDetail(shanghuid);
        getShopBelowDetail(shanghuid);


    }

    @Override
    public void getShopUpDetail(String shanghuid) {
        HttpHelper.getInstance().getShanghuUpDetail(shanghuid);
    }

    @Override
    public void getShopBelowDetail(String shanghuid) {
        HttpHelper.getInstance().getShanghuFuwu(shanghuid);
        HttpHelper.getInstance().getShanghuFuwuyuangong(shanghuid);
        HttpHelper.getInstance().getShanghuPinglun(shanghuid);
    }
}
