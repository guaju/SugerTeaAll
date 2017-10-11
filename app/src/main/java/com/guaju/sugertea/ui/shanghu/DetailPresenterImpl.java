package com.guaju.sugertea.ui.shanghu;

import com.guaju.sugertea.httputil.HttpHelper;

/**
 * Created by guaju on 2017/9/18.
 */

public class DetailPresenterImpl implements ShanghuDetailContract.DetaiPresenter {
    private ShanghuDetailContract.DetailView view;

    public DetailPresenterImpl(ShanghuDetailContract.DetailView view) {
        this.view = view;
    }

    @Override
    public void load(String shanghuid) {
        //去通过商户id去加载数据
        HttpHelper.getInstance().getShanghuDetail(shanghuid);
    }
}
