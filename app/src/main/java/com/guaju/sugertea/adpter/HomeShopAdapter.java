package com.guaju.sugertea.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.guaju.sugertea.R;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.HomeShopListBean;
import com.guaju.sugertea.ui.shanghu.ShanghuDetailActivity;

import java.util.ArrayList;


/**
 * Created by guaju on 2017/9/13.
 */

public class HomeShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private static final String TAG = "HomeShopAdapter";
    private static final int PROGRESS = 301;
    private static final int NORMAL = 300;
    private int mPosition=0;

    Context context;
    ArrayList<HomeShopListBean.ListBean> lists;
    //定义一个表示，看是否是显示的是总条数的最后一条
    boolean  isEnd=false;

    public HomeShopAdapter(Context context, ArrayList<HomeShopListBean.ListBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (NORMAL==viewType){
            //说明是一个正常的条目
        View v = LayoutInflater.from(context).inflate(R.layout.item_home_shop, parent, false);
        return new MyViewHolder(v);
        }else {
            View progress = LayoutInflater.from(context).inflate(R.layout.progress_home_shop, parent, false);
          return new ProgressViewHolder(progress);
        }


    }


     //在绑定viewholder的时候添加点击事件，接口回调的方式
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
           if (getItemViewType(position)==NORMAL){
               //说明他是一个正常的条目
               MyViewHolder mViewHolder = (MyViewHolder) holder;
               mViewHolder.setContent(lists.get(position));
               //布局排序位置
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //跳转页面到商品详情
                       Activity context = (Activity) HomeShopAdapter.this.context;
                       Intent intent = new Intent(context, ShanghuDetailActivity.class);
                       Intent shanghuid = intent.putExtra("shanghuid", lists.get(position).getId());
                       context.startActivity(intent);
                   }
               });

           }


    }


    //当recyclerview滑动到最底部，滑动当最底部条目出来的时候就展示总条数+1，这个1就是我们要展示的进度条
    @Override
    public int getItemCount() {
        if (isEnd){
            return lists.size()+1;
        }else{
            return lists.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==lists.size()){
            return PROGRESS;
        } else{

        return NORMAL;
        }
    }



    public  class MyViewHolder extends RecyclerView.ViewHolder{
     SimpleDraweeView iv_homeshop;
     TextView tv_title_homeshop,tv_desc_homeshop,tv_cuxiao_homeshop,tv_quan_homeshop,tv_distance_homeshop;
     RatingBar ratingbar_homeshop;
     LinearLayout ll_cuxiao_homeshop,ll_quan_homeshop;

     public MyViewHolder(View itemView) {
         super(itemView);
         iv_homeshop=(SimpleDraweeView)itemView.findViewById(R.id.iv_homeshop);
         tv_title_homeshop=(TextView)itemView.findViewById(R.id.tv_title_homeshop);
         tv_desc_homeshop=(TextView)itemView.findViewById(R.id.tv_desc_homeshop);
         tv_cuxiao_homeshop=(TextView)itemView.findViewById(R.id.tv_cuxiao_homeshop);
         tv_distance_homeshop=(TextView)itemView.findViewById(R.id.tv_distance_homeshop);
         tv_quan_homeshop=(TextView)itemView.findViewById(R.id.tv_quan_homeshop);
         ratingbar_homeshop=(RatingBar)itemView.findViewById(R.id.ratingbar_homeshop);
         ll_cuxiao_homeshop=(LinearLayout)itemView.findViewById(R.id.ll_cuxiao_homeshop);
         ll_quan_homeshop=(LinearLayout)itemView.findViewById(R.id.ll_quan_homeshop);
         //初始化pipeline

     }
     //专门提供设置内容的方法
     public  void  setContent(HomeShopListBean.ListBean bean){
         //图片设置
//         Glide.with(context).
//                 load(Constant.IMAGE_SHOP+bean.getLogo()).
//                 listener(new MyRequestListener()).
//                 into(iv_homeshop);

         ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(Constant.IMAGE_SHOP+bean.getLogo()))
                 .setProgressiveRenderingEnabled(true)
                 .build();
         DraweeController controller = Fresco.newDraweeControllerBuilder()
                 .setImageRequest(request)
                 .setOldController(iv_homeshop.getController())
                 .build();
         iv_homeshop.setController(controller);



         tv_title_homeshop.setText(bean.getMingcheng());
         tv_desc_homeshop.setText(bean.getJianjie());
         int juli = bean.getJuli();
         float fJuli = juli * 0.001f;
         tv_distance_homeshop.setText(fJuli+"km");
         ratingbar_homeshop.setRating(Integer.parseInt(bean.getPingji()));
         int cuxiao = bean.getCuxiao();
         if (cuxiao==0){
             //说明没有促销
             ll_cuxiao_homeshop.setVisibility(View.GONE);
         }else if(cuxiao==1){
             ll_cuxiao_homeshop.setVisibility(View.VISIBLE);
             tv_cuxiao_homeshop.setText(bean.getCuxiaofuwumingcheng());
         }
         //TODO 万一有两个就有异常
         int gongkaikaquan = bean.getGongkaikaquan();
         if (gongkaikaquan==0){
             //说明没有促销
             ll_quan_homeshop.setVisibility(View.GONE);
         }else if(gongkaikaquan==1){
             ll_quan_homeshop.setVisibility(View.VISIBLE);
             tv_quan_homeshop.setText(bean.getKaquanmingcheng());
         }
     }
 }

 //提供一个方法，让调用者设置这个标识值
    public void  setIsEnd(boolean isEnd){
        this.isEnd=isEnd;
    }

    //定义一个progress的viewholder
    public  class   ProgressViewHolder extends RecyclerView.ViewHolder{

        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
    class MyRequestListener implements RequestListener{

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target target, boolean b) {
            Glide.with(context).pauseRequests();//暂停加载
            Toast.makeText(context, "网络多慢，请检查您的网络", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onResourceReady(Object o, Object o2, Target target, DataSource dataSource, boolean b) {
            return false;
        }
    }
    @Override
    public void onClick(View v) {
     //根据list中的数据做出

    }

}
