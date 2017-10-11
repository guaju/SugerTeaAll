package com.guaju.sugertea.ui.shanghu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.guaju.sugertea.R;
import com.guaju.sugertea.model.bean.Shanghu;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by guaju on 2017/9/18.
 */

public class ShanghuDetailActivity extends AppCompatActivity implements ShanghuDetailContract.DetailView{
    ShanghuDetailContract.DetaiPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shanghu_detail);
        //初始化presenter
        presenter=new DetailPresenterImpl(this);
        EventBus.getDefault().register(this);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String shanghuid = intent.getStringExtra("shanghuid");
        presenter.load(shanghuid);

    }

    @Override
    public void showInfo() {
       //展示页面详情

    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onComplete(Shanghu bean){
        Toast.makeText(this, bean.getDianpu().getJianjie()+"", Toast.LENGTH_SHORT).show();
        showInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
