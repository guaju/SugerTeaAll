package com.guaju.sugertea.ui.shopdetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guaju.sugertea.R;
import com.guaju.sugertea.adpter.ShopDetailViewPagerAdaper;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.ShanghuFuwuListBean;
import com.guaju.sugertea.model.bean.ShanghuFuwuyuangongBean;
import com.guaju.sugertea.model.bean.ShanghuPinglunBean;
import com.guaju.sugertea.model.bean.ShanghuUpDetailsBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by guaju on 2017/10/11.
 */

public class ShopDetailActivity extends Activity implements ShopDetailContract.View {
    @BindView(R.id.draweeview_icon)
    SimpleDraweeView draweeviewIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.tv_yingyeshijian)
    TextView tvYingyeshijian;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_juli)
    TextView tvJuli;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ShopDetailContract.presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);
        ButterKnife.bind(this);
        presenter = new ShopDetailPresenterImpl(this);


        EventBus.getDefault().register(this);
        String shanghuid = getIntent().getStringExtra("shanghuid");
        if (!TextUtils.isEmpty(shanghuid)) {
            //获取商户详情数据
            presenter.getShopDetail(shanghuid);
        }
        initVpData();
    }

    private void initVpData() {
        initBelowItemList();

        ArrayList<ImageView> lists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
            lists.add(imageView);
        }
        ShopDetailViewPagerAdaper adaper = new ShopDetailViewPagerAdaper(lists);
        vp.setAdapter(adaper);
        bindTab2Vp(tablayout, vp);
    }

    //初始化本页面下方的list
    void initBelowItemList() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.shopdetaillist, null, false);
        ButterKnife.bind(this,v);
        LinearLayoutManager lmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(lmanager);


    }

    @Override
    public void bindTab2Vp(TabLayout tab, ViewPager viewPager) {
        //先给tab设置文字
        tab.setTabMode(TabLayout.MODE_FIXED);
        //该方法会把tablayout原有的tab标签全都移除掉，然后去viewpager的适配器中去找
        //getPageTitle(i) ,这个方法会返回string，那么意思就是说一这个为标签名，去给tablaout添加tab

        tab.setupWithViewPager(viewPager);

    }

    @Override
    public void setUpView(ShanghuUpDetailsBean bean) {
        ShanghuUpDetailsBean.DianpuBean dianpu = bean.getDianpu();
        String imgurl = Constant.IMAGE_SHOP + dianpu.getCover();
        Uri imguri = Uri.parse(imgurl);
        draweeviewIcon.setImageURI(imguri);
        String mingcheng = dianpu.getMingcheng();
        tvName.setText(mingcheng);
        tvYingyeshijian.setText("营业时间:" + dianpu.getKaishishijian() + "-" + dianpu.getJieshushijian());
        String dizhi = dianpu.getDizhi();
        tvAdress.setText(dizhi);
        int juli = dianpu.getJuli();
        float fjuli = juli * 0.001f;
        tvJuli.setText(fjuli + "");
        int pingfen = dianpu.getPingfen();
        ratingbar.setRating(pingfen);
        final String dianhua = dianpu.getDianhua();
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + dianhua));
                startActivity(intent);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setContent(ShanghuUpDetailsBean bean) {
        //TODO 处理数据
        setUpView(bean);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setFuwuList(ShanghuFuwuListBean bean) {

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setFuwuyuangongList(ShanghuFuwuyuangongBean bean) {

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setPinglunList(ShanghuPinglunBean bean) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
