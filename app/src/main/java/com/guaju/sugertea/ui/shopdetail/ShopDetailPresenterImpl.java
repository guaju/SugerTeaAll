package com.guaju.sugertea.ui.shopdetail;

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



    }
}
