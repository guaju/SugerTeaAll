package com.guaju.sugertea.httputil;

import com.guaju.sugertea.api.API;
import com.guaju.sugertea.api.APILYB;
import com.guaju.sugertea.app.App;
import com.guaju.sugertea.constant.BSConstant;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.dao.bean.DaoSession;
import com.guaju.sugertea.dao.bean.UserInfo;
import com.guaju.sugertea.dao.bean.UserInfoDao;
import com.guaju.sugertea.model.bean.ADBean;
import com.guaju.sugertea.model.bean.BaseBean;
import com.guaju.sugertea.model.bean.HomeShopBean;
import com.guaju.sugertea.model.bean.HomeShopListBean;
import com.guaju.sugertea.model.bean.LoginBean;
import com.guaju.sugertea.model.bean.LoginInfo;
import com.guaju.sugertea.model.bean.Shanghu;
import com.guaju.sugertea.model.bean.ShanghuFuwuListBean;
import com.guaju.sugertea.model.bean.ShanghuFuwuyuangongBean;
import com.guaju.sugertea.model.bean.ShanghuPinglunBean;
import com.guaju.sugertea.model.bean.ShanghuUpDetailsBean;
import com.guaju.sugertea.model.bean.TuijianShopBean;
import com.guaju.sugertea.model.bean.UserInfoBean;
import com.guaju.sugertea.model.lybbean.Test;
import com.guaju.sugertea.ui.home.HomeFragment;
import com.guaju.sugertea.ui.login.LoginActivity;
import com.guaju.sugertea.ui.main.MainActivity;
import com.guaju.sugertea.utils.AppUtil;
import com.guaju.sugertea.utils.SPUtils;

import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by guaju on 2017/8/21.
 */

public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static HttpHelper helper = new HttpHelper();
    private Retrofit retrofit;
    private Retrofit retrofitlyb;
    private final API api;
    private final APILYB apilyb;
    private final OkHttpClient okHttpClient;

    private HttpHelper() {
        okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(3, TimeUnit.SECONDS)
//                .readTimeout(3, TimeUnit.SECONDS)
//                .writeTimeout(3, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ROOT + Constant.URL_VERSION)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        retrofitlyb = new Retrofit.Builder()
                .baseUrl("http://testhf.irongbei.com/AppApi4/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(API.class);
        apilyb = retrofitlyb.create(APILYB.class);
       // 49932DFEA05F61D1378BF970E4B30726F6806479A29E
       // 49932DFEA05F1BE6ED4C8692F8EB1B10EF0522D3B779

    }

    public static HttpHelper getInstance() {
        return helper;
    }

    /*
     访问商户列表
      */
    public void getShopList(String openId, String location) {
        Observable<HomeShopBean> jingxuanshanghu = api.homeShop("jingxuanshanghu", openId, 0, location, 1);
        jingxuanshanghu.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HomeShopBean>() {
                    @Override
                    public void call(HomeShopBean homeShopBean) {
                        EventBus.getDefault().post(new MainActivity.MyEvent(homeShopBean));
                    }
                });

    }

    /*
    发送验证码
     */
    public void getVerifyCode(String phone, long time, String ip, String mac) {
        Observable<BaseBean> verifycode = api.getVerifycode(BSConstant.VERIFY_CODE, phone, ip, mac, time, AppUtil.encryptSign((time - 444) + "", "", phone, ip), "1");
        verifycode.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                    }
                });
    }

    /*
   登录
    */
    public void login(final String phone, final String code) {
        Observable<BaseBean<LoginBean>> baseBean = api.login(BSConstant.LOGIN, phone, code, null, null, null, null, null);
        baseBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<LoginBean>>() {
                    @Override
                    public void call(BaseBean<LoginBean> baseBean) {
                        if (baseBean.getCode() == 200) {
                            LoginBean obj = baseBean.getObj();
                            LoginInfo loginInfo = new LoginInfo(phone, code, obj);
                            UserInfoBean user = obj.getUser();
//
//                           user.getAvatar();//头像
//                           user.getNickname();//昵称
//                           user.getPhone();//电话号码
//                           user.getSex();//拿到性别
//                           user.getXiadanshu();//订单数量
//                           user.getShoucangshanghu();//收藏店铺
//                           user.getYouhuiquan();//优惠券
//                           user.getHuiyuanka();//会员卡

                            SPUtils instance = SPUtils.getInstance(App.appContext, Constant.SPNAME);
                            instance.putSp(Constant.SP_PHONENUM, phone);
                            instance.putSp(Constant.SP_LOGINCODE, code);
                            instance.putSp(Constant.SP_ISLOGIN, true);

                            //跟以前的token一样，可以携带用户数据
                            DaoSession daoSession = App.getDaoSession();
                            UserInfo userInfo = new UserInfo(phone,
                                    user.getOpenid(),
                                    user.getNickname(),
                                    user.getXiadanshu(),
                                    user.getShoucangshanghu(),
                                    user.getYouhuiquan(),
                                    user.getHuiyuanka(),
                                    user.getAvatar());
                            //如果主键存在就替换，如果不存在就存入
                            daoSession.insertOrReplace(userInfo);
                            EventBus.getDefault().post(userInfo);

                            EventBus.getDefault().post(new LoginActivity.FinishEvent());


                        } else {
                            EventBus.getDefault().post("ERROR");

                        }

                    }
                });
    }

    /*
    拿到广告轮播图片
     */
    public void getAD(String zuobiao) {
        Observable<BaseBean<ADBean>> ad = api.getAD(BSConstant.AD, zuobiao);
        ad.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<ADBean>>() {
                    @Override
                    public void call(BaseBean<ADBean> baseBean) {
                        if (baseBean.getCode()==200){
                        ADBean obj = baseBean.getObj();
                        List<ADBean.ContentBean> content = obj.getContent();
                        EventBus.getDefault().post(content);
                    }else{
                            EventBus.getDefault().post(new HomeFragment());
                        }
                    }
                });
    }
    /*
    获取首页推荐商户的信息
     */
    public  void  getTuijianShops(){
        Observable<BaseBean<TuijianShopBean>> shops = api.getTuijianSHops(BSConstant.HOME_COLLECTION);
        shops.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<TuijianShopBean>>() {
                    @Override
                    public void call(BaseBean<TuijianShopBean> shops) {
                        TuijianShopBean obj = shops.getObj();
                        EventBus.getDefault().post(obj);
                    }
                });
    }

    public void  lybLogin(String username,String pass,String num,String sign){
        Observable<Test> login = apilyb.login(username, pass, num, sign);
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Test>() {
                    @Override
                    public void call(Test test) {
                    }
                });
    }
    /**
     * 得到首页商户列表
     */
    public void  getHomeShopList(String openid,String paixu,String zuobiao,String page){
        final Observable<BaseBean<HomeShopListBean>> homeListBean = api.getHomeListBean(BSConstant.SHOP_LIST, openid, paixu, zuobiao, page);
        homeListBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseBean<HomeShopListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        EventBus.getDefault().post(throwable);
                        //Toast.makeText(App.appContext, "网络异常，请检查您的网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(BaseBean<HomeShopListBean> homeShopListBeanBaseBean) {
                        if (homeShopListBeanBaseBean.getCode()==200){
                            HomeShopListBean obj = homeShopListBeanBaseBean.getObj();
                            //往homefragment发送事件消息
                            EventBus.getDefault().post(obj);

                        }

                    }
                });
    }
    /**
     * 获得商户详情
     */
   public void getShanghuDetail(String shanghuid){
       //获取openid
       DaoSession daoSession = App.getDaoSession();
       //通过daoSession去拿到真正的管理者
       UserInfoDao userInfoDao = daoSession.getUserInfoDao();
       SPUtils sp = SPUtils.getInstance(App.appContext, Constant.SPNAME);
       String phone = (String) sp.getSp(Constant.SP_PHONENUM, String.class);
       UserInfo load = userInfoDao.load(phone);
       String openId = load.getOpenId();
       //获取坐
       String location = (String) sp.getSp(Constant.SP_LOCATION, String.class);
       Observable<BaseBean<Shanghu>> shanghuDetail = api.getShanghuDetail(BSConstant.SHOP_DETAIL, openId, shanghuid, location);
       shanghuDetail.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Action1<BaseBean<Shanghu>>() {
                   @Override
                   public void call(BaseBean<Shanghu> shanghuBaseBean) {
                       Shanghu obj = shanghuBaseBean.getObj();
                       //把获取到的Shanghu，传给商户详情activity
                       EventBus.getDefault().post(obj);
                   }
               });
   }
   /*
   获得商户上方详情
    */
   public  void getShanghuUpDetail(String shanghuid){
       //获取openid
       DaoSession daoSession = App.getDaoSession();
       //通过daoSession去拿到真正的管理者
       UserInfoDao userInfoDao = daoSession.getUserInfoDao();
       SPUtils sp = SPUtils.getInstance(App.appContext, Constant.SPNAME);
       String phone = (String) sp.getSp(Constant.SP_PHONENUM, String.class);
       UserInfo load = userInfoDao.load(phone);
       String openId = load.getOpenId();
       //获取坐
       String location = (String) sp.getSp(Constant.SP_LOCATION, String.class);
       Observable<BaseBean<ShanghuUpDetailsBean>> shanghuDetail = api.getShanghuUpDetail(BSConstant.SHOP_DETAIL, openId, shanghuid, location);
       shanghuDetail.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Action1<BaseBean<ShanghuUpDetailsBean>>() {
                   @Override
                   public void call(BaseBean<ShanghuUpDetailsBean> bean) {
                       ShanghuUpDetailsBean obj = bean.getObj();
                       //把获取到的Shanghu，传给商户详情activity
                       EventBus.getDefault().post(obj);
                   }
               });
   }
    /**
     * 获得商户服务列表
     */
    public void getShanghuFuwu(String shanghuid){
        Observable<BaseBean<ShanghuFuwuListBean>> shanghuFuwuList = api.getShanghuFuwuList(BSConstant.SERVICE_LIST, shanghuid, "1");
        shanghuFuwuList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<ShanghuFuwuListBean>>() {
                    @Override
                    public void call(BaseBean<ShanghuFuwuListBean> bean) {
                        EventBus.getDefault().post(bean);
                    }
                });
    }
    /*
    获得商户服务员工列表
     */
    public void getShanghuFuwuyuangong(String shanghuid){
        Observable<BaseBean<ShanghuFuwuyuangongBean>> shanghuFuwuList = api.getShanghuFuwuyuangongList(BSConstant.SHOP_STAFF, shanghuid, "1");
        shanghuFuwuList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<ShanghuFuwuyuangongBean>>() {
                    @Override
                    public void call(BaseBean<ShanghuFuwuyuangongBean> bean) {
                        EventBus.getDefault().post(bean);
                    }
                });
    }

    /**
     * 获得商户评论列表
     */
    public void getShanghuPinglun(String shanghuid){
        Observable<BaseBean<ShanghuPinglunBean>> shanghuFuwuList = api.getShanghuPinglun(BSConstant.SHOP_COMMENT, shanghuid, "1");
        shanghuFuwuList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseBean<ShanghuPinglunBean>>() {
                    @Override
                    public void call(BaseBean<ShanghuPinglunBean> bean) {
                        EventBus.getDefault().post(bean);
                    }
                });
    }


}
