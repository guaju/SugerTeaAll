package com.guaju.sugertea.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.guaju.sugertea.R;
import com.guaju.sugertea.adpter.HomeShopAdapter;
import com.guaju.sugertea.app.App;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.dao.bean.DaoSession;
import com.guaju.sugertea.dao.bean.UserInfo;
import com.guaju.sugertea.dao.bean.UserInfoDao;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.bean.ADBean;
import com.guaju.sugertea.ui.main.MainActivity;
import com.guaju.sugertea.ui.shopdetail.ShopDetailActivity;
import com.guaju.sugertea.utils.SPUtils;
import com.guaju.sugertea.widget.CustomScrollView;

import java.util.List;

import static com.guaju.sugertea.ui.home.HomeFragment.SCROLLTAG;
import static com.guaju.sugertea.ui.home.HomeFragment.SCROLLTAGDELAY;

/**
 * Created by guaju on 2017/8/30.
 */

public class HomePresenterImpl implements HomeContract.HomePresenter {
    private HomeContract.HomeView view;
    HomeFragment fragment;
    private int touchY;

    @Override
    public void requestTuijianShops() {
        HttpHelper.getInstance().getTuijianShops();
    }

    @Override
    public void requestADdata(String zuobiao) {
        HttpHelper.getInstance().getAD(zuobiao);
    }


    @Override
    public void setADdata(List<ADBean.ContentBean> content, SliderLayout slider) {
        fragment = (HomeFragment) this.view;
        for (final ADBean.ContentBean bean : content) {
            DefaultSliderView textSliderView = new DefaultSliderView(fragment.getActivity());
            String url = Constant.IMAGE_AD + bean.getPic();
            textSliderView.image(url);
            //sliderlayout没有自己的onitemlistener类似的方法，只能给它里面的sliderview单个添加点击事件
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Intent intent = new Intent(fragment.getActivity(), ShopDetailActivity.class);
                    //添加商户id的信息
                    String shanghuid = bean.getShanghuid();
                    intent.putExtra("shanghuid",shanghuid);
                    //如果不是用activity跳转的页面，可能跳不过去，需要加Flag
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    fragment.getActivity().startActivity(intent);
                }
            });
            slider.addSlider(textSliderView);
        }
        //如果只有一个数据，就停止自动轮播
        if(content.size()==1){
            slider.stopAutoCycle();
        }else{
            slider.startAutoCycle();
        }

    }


    @Override
    public void requestHomeListData(String paixu, String page) {

        fragment = (HomeFragment) this.view;
        //TODO 获取首页list信息
        DaoSession daoSession = App.getDaoSession();
        //先我们存储的bean的dao 管理者
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        SPUtils sp = SPUtils.getInstance(fragment.getActivity(), Constant.SPNAME);
        String phonenum = (String) sp.getSp("phonenum", String.class);
        String location = (String) sp.getSp("location", String.class);

        //传入key
        UserInfo load = userInfoDao.load(phonenum);
        String openId = load.getOpenId();
        HttpHelper.getInstance().getHomeShopList(
                TextUtils.isEmpty(phonenum) ? null : phonenum,
                paixu,
                TextUtils.isEmpty(location) ? null : location,
                page);
    }

    @Override
    public void setHomeListAdapter(HomeShopAdapter homeShopAdapter) {
        fragment.rv_list.setAdapter(homeShopAdapter);
    }

    public HomePresenterImpl(HomeContract.HomeView view) {
        this.view = view;
    }
    //控制动静条的隐藏显示
    @Override
    public void controlMenuDisplay() {
        //给scroll设置一个滑动监听
        final int locationdong[] = new int[2]; //第一个数保存的是view距离屏幕坐标的值，第二个是距离顶部的值
        final int locationjing[] = new int[2];
        fragment = (HomeFragment) this.view;
        //为滚动的操作添加handler
        Handler mScrollHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (SCROLLTAG == msg.what) {
                    if (touchY != fragment.ptrsv.getScrollY()) {
                        //说明惯性事件发生，手指猛滑会发生
                        return;
                    }
                 /*
                如果用的是scrollview可以用这个
                 */
//                拿到动静menu的坐标
                    fragment.menu_dong.getLocationOnScreen(locationdong);
                    fragment.menu_jing.getLocationOnScreen(locationjing);
//                判断view距离顶部的值
                    if (locationdong[1] > locationjing[1]) {
                        fragment.menu_jing.setVisibility(View.GONE);
                    } else {
                        fragment.menu_jing.setVisibility(View.VISIBLE);
                    }
                    //__________________________________________________________________________________
                }
                if (SCROLLTAGDELAY == msg.what) {
                    //此判断仅仅是避免多次执行这里面的操作
                    if (touchY != fragment.ptrsv.getScrollY()) {
                        fragment.menu_dong.getLocationOnScreen(locationdong);
                        fragment.menu_jing.getLocationOnScreen(locationjing);
                        if (locationdong[1] > locationjing[1]) {
                            fragment.menu_jing.setVisibility(View.GONE);
                        } else {
                            fragment.menu_jing.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }
        };
        initScrollViewListener(fragment,mScrollHandler);
    }

    //为scrollview添加监听事件
    private void initScrollViewListener(final HomeFragment fragment, final Handler mScrollHandler) {
        touchY = 0;
        final MainActivity ma = (MainActivity) fragment.getActivity();
        final int vpheight = ma.getResources().getDimensionPixelSize(R.dimen.viewpagerheightplus);
        fragment.ptrsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当手指滑动或者当手指抬起的时候给handler发送事件，让handler处理滚动的逻辑
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //记录当时的scrolly值 ，不会出现惯性的事件
                    touchY = fragment.ptrsv.getScrollY();
                    mScrollHandler.sendEmptyMessage(SCROLLTAG);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当我up的时候touchY就不变了 ，只有当up的时候才会出现惯性事件
                    touchY = fragment.ptrsv.getScrollY();
                    mScrollHandler.sendEmptyMessage(SCROLLTAG);
                    //可能会有flying动作
                }
                //不管move还是up都延迟发送消息，并且消息是另外一个what值
                mScrollHandler.sendEmptyMessageDelayed(SCROLLTAGDELAY, 100);
                return false;
            }
        });
        //接口回调
        //5.让用户传入接口
        //背景渐变效果
        fragment.ptrsv.setScrollViewListener(new CustomScrollView.ScrollViewListener() {
            @Override
            public void onscroll(CustomScrollView csv, int t) {
                if (t <= 0) {
                    // 只是layout背景透明(仿知乎滑动效果)
                    ma.customActionbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                    //添加条目

                } else {
                    //大于某值得时候就需要设置透明度

                    float scale = (float) t / vpheight;  //透明度按照比例去设置
                    float alpha = (255 * scale);  //算出当前的透明度值
                    // 只是layout背景透明(仿知乎滑动效果)
                    int alphaint = (int) alpha;
                    //因为算出来的值可能永远不能等于255，所以这么设置
                    if (alpha >= 250) {
                        alphaint = 255;
                    }
                    ma.customActionbar.setBackgroundColor(Color.argb(alphaint, 255, 255, 255));
                }
            }
        });
    }   //可能会有flying动作


}
