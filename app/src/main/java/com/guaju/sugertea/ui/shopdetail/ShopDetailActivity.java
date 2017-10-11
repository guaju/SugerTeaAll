package com.guaju.sugertea.ui.shopdetail;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guaju.sugertea.R;
import com.guaju.sugertea.adpter.ShopDetailViewPagerAdaper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guaju on 2017/10/11.
 */

public class ShopDetailActivity extends Activity implements ShopDetailContract.View{
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
    private ShopDetailContract.presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_details);
        ButterKnife.bind(this);
        presenter = new ShopDetailPresenterImpl(this);

        String shanghuid = getIntent().getStringExtra("shanghuid");
        if (!TextUtils.isEmpty(shanghuid)){
            presenter.getShopDetail(shanghuid);
        }

        initVpData();
    }

    private void initVpData() {
        ArrayList<ImageView> lists=new ArrayList<>();
        for (int i=0;i<3;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
            lists.add(imageView);
        }
        ShopDetailViewPagerAdaper adaper = new ShopDetailViewPagerAdaper(lists);
        vp.setAdapter(adaper);
        bindTab2Vp(tablayout,vp);
    }

    @Override
    public void bindTab2Vp(TabLayout tab,ViewPager viewPager) {
        //先给tab设置文字
        tab.setTabMode(TabLayout.MODE_FIXED);
        //该方法会把tablayout原有的tab标签全都移除掉，然后去viewpager的适配器中去找
        //getPageTitle(i) ,这个方法会返回string，那么意思就是说一这个为标签名，去给tablaout添加tab

        tab.setupWithViewPager(viewPager);

    }
}
