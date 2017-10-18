package com.guaju.sugertea.ui.shopdetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guaju.sugertea.R;
import com.guaju.sugertea.adpter.ShopDetailBelowAdapter;
import com.guaju.sugertea.adpter.ShopDetailViewPagerAdaper;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.bean.ShanghuFuwuListBean;
import com.guaju.sugertea.model.bean.ShanghuFuwuyuangongBean;
import com.guaju.sugertea.model.bean.ShanghuUpDetailsBean;
import com.guaju.sugertea.model.bean.ShopCommentBean;

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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rv_icon)
    RelativeLayout rvIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.tv_yingyeshijian)
    TextView tvYingyeshijian;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_juli)
    TextView tvJuli;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private ShopDetailContract.presenter presenter;
    private RecyclerView recyclerview;
    private ArrayList<View> recyclerViews;
    private int zongtiaoshu;
    private String[] tabNames;
    private String shanghuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_detail);
        ButterKnife.bind(this);
        presenter = new ShopDetailPresenterImpl(this);


        EventBus.getDefault().register(this);
        shanghuid = getIntent().getStringExtra("shanghuid");
        //
        shanghuid=1449+"";
        if (!TextUtils.isEmpty(shanghuid)) {
            //获取商户详情数据
            presenter.getShopDetail(shanghuid);
        }
        initVpData();
    }

    private void initVpData() {
        initBelowItemList();


    }

    //初始化本页面下方的list
    void initBelowItemList() {
        recyclerViews = new ArrayList<>();


    }

    //创建recyclerview,根据list是否为空，控制界面的显示
    private void createList(ArrayList list) {
        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(itemlayout, null, false);
//        recyclerview = (RecyclerView) v.findViewById(R.id.recyclerview);
        //如果list是空的就展示空的页面
        if (list == null || list.isEmpty()) {
            FrameLayout fl = (FrameLayout) inflater.inflate(R.layout.shopdetail_image_item, null, false);
            recyclerViews.add(fl);
        } else {
            recyclerview = (RecyclerView) inflater.inflate(R.layout.shopdetail_list_item, null, false);
            //当你出现scroLlview嵌套recyclerview出现显示不全的问题时，你需要重写layoutmanager
            LinearLayoutManager lmanager = new LinearLayoutManager(this);
            recyclerview.setLayoutManager(lmanager);
            ShopDetailBelowAdapter shopDetailBelowAdapter = new ShopDetailBelowAdapter(list, this);
            recyclerview.setAdapter(shopDetailBelowAdapter);
            recyclerViews.add(recyclerview);
        }
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
        int juli = Integer.parseInt(dianpu.getJuli());
        float fjuli = juli * 0.001f;
        tvJuli.setText(fjuli + "");
        float pingfen = Float.parseFloat(dianpu.getPingfen());
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

    //设置tablayout的信息
    @Override
    public void setTabLayoutTabs(int commentNum) {
        tabNames = new String[]{"服务列表", "服务人员", "评论（" + commentNum + "）"};

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setContent(ShanghuUpDetailsBean bean) {
        //TODO 处理数据
        setUpView(bean);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setFuwuList(ShanghuFuwuListBean bean) {
        ArrayList<ShanghuFuwuListBean.ListBean> list = (ArrayList<ShanghuFuwuListBean.ListBean>) bean.getList();
        //需要判断数据是否为空
        createList(list);
        //通过eventbus 完成三个访问根据链式结构进行访问
        HttpHelper.getInstance().getShanghuFuwuyuangong(shanghuid);


    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setFuwuyuangongList(ShanghuFuwuyuangongBean bean) {
        ArrayList<ShanghuFuwuyuangongBean.ListBean> list = (ArrayList<ShanghuFuwuyuangongBean.ListBean>) bean.getList();
        createList(list);

        HttpHelper.getInstance().getShanghuPinglun(shanghuid);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setPinglunList(ShopCommentBean bean) {
        zongtiaoshu = Integer.parseInt(bean.getZongtiaoshu());
        setTabLayoutTabs(zongtiaoshu); //设置tablayout上方的名称
        //首先访问的是评论列表
        ArrayList<ShopCommentBean.Comment> list = bean.getList();
        createList(list);
        //这是最后一次请求网络的回调，我们需要在这去设置viewpager的适配器
        ShopDetailViewPagerAdaper adaper = new ShopDetailViewPagerAdaper(recyclerViews, tabNames);
        vp.setAdapter(adaper);
        bindTab2Vp(tablayout, vp);


//         ll.setMinimumHeight(MeasureUtils.getScreenHeight1(this));
//         vp.setMinimumHeight(MeasureUtils.getScreenHeight1(this));

//     Socket 客户端  ip+端口号
//                |流
//     ServerSocket  服务端  监听一个端口号    一直处于阻塞状态

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
