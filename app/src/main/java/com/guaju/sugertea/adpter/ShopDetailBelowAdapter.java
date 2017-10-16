package com.guaju.sugertea.adpter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guaju.sugertea.R;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.ShanghuFuwuListBean;
import com.guaju.sugertea.model.bean.ShanghuFuwuyuangongBean;
import com.guaju.sugertea.model.bean.ShopCommentBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guaju on 2017/10/12.
 */

public class ShopDetailBelowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList lists;
    int SERVICENAME = 100;
    int SERVICESTAFF = 101;
    int COMMENT = 102;
    Context context;
    LayoutInflater inflater;
    private View view;

    public ShopDetailBelowAdapter(ArrayList lists, Context context) {
        this.lists = lists;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SERVICENAME) {
            view = inflater.inflate(R.layout.item_shopdetail_servicename, parent, false);
             return view != null ? new ServiceNameViewHolder(view) : null;
        }else if(viewType==SERVICESTAFF){
            view = inflater.inflate(R.layout.item_shopdetail_servicestaff, parent, false);
            return view != null ? new ServiceStaffViewHolder(view) : null;
        }
        else{
            return view != null ? new CommentViewHolder(view) : null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //通过getviewtype转化成不一样的类型
        if (getItemViewType(position)==SERVICENAME){
        ShanghuFuwuListBean.ListBean o = (ShanghuFuwuListBean.ListBean) lists.get(position);
        ServiceNameViewHolder holder1 = (ServiceNameViewHolder) holder;
        holder1.setContent(o);
        } else if (getItemViewType(position)==SERVICESTAFF){
            ShanghuFuwuyuangongBean.ListBean o = (ShanghuFuwuyuangongBean.ListBean) lists.get(position);
            ServiceStaffViewHolder holder2 = (ServiceStaffViewHolder) holder;
            holder2.setContent(o); 
        }
        else{
            ShopCommentBean.Comment o = (ShopCommentBean.Comment) lists.get(position);
            CommentViewHolder holder3 = (CommentViewHolder) holder;
            holder3.setContent(o);
        }
        
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object o = lists.get(0);
        if (o instanceof ShanghuFuwuListBean.ListBean) {
            return SERVICENAME;
        } else if (o instanceof ShanghuFuwuyuangongBean.ListBean) {
            return SERVICESTAFF;
        } else {
            return COMMENT;
        }
    }

    class ServiceNameViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.service_icon)
        SimpleDraweeView serviceIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_danwei)
        TextView tvDanwei;
        @BindView(R.id.buy_num)
        TextView buyNum;
        @BindView(R.id.tv_buy)
        TextView tvBuy;

        public ServiceNameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_buy)
        public void onViewClicked() {
            //预约的处理

        }

        public void setContent(ShanghuFuwuListBean.ListBean bean) {
            String name = bean.getName();
            String danwei = bean.getDanwei();
            String jianjie = bean.getJianjie();
            String jine = bean.getJine();
            String photo = Constant.IMAGE_SERVICE + bean.getPhoto();
            int yuyueshu = bean.getYuyueshu();
            serviceIcon.setImageURI(Uri.parse(photo));
            tvName.setText(name);
            tvDanwei.setText(danwei);
            tvPrice.setText(jine);
            tvDesc.setText(jianjie);
            buyNum.setText(yuyueshu == 0 ? "" : "已约" + yuyueshu + "单");
        }

    }

    class ServiceStaffViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ic_avatar)
        SimpleDraweeView icAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        public ServiceStaffViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setContent(ShanghuFuwuyuangongBean.ListBean bean) {
            String avatar = Constant.IMAGE_AVATAR + bean.getAvatar();
            icAvatar.setImageURI(avatar);
            tvName.setText(bean.getNickname());
            ratingBar.setRating(bean.getFuwupingfen());

        }
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ic_avatar)
        SimpleDraweeView icAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_comment)
        TextView tvComment;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void setContent(ShopCommentBean.Comment bean){
            ShopCommentBean.Comment.UserBean user = bean.getUser();
            String avatar = Constant.IMAGE_AVATAR + user.getAvatar();
            icAvatar.setImageURI(avatar);
            tvName.setText(user.getNickname()==null?user.getPhone():user.getNickname());
            tvComment.setText(bean.getNeirong());

            SimpleDateFormat sdf=new SimpleDateFormat("mm-dd hh-MM");
            long l = Long.parseLong(bean.getShijian()) * 1000;
            Date date = new Date(l);
            String format = sdf.format(date);
            tvTime.setText(format);
            ratingBar.setRating(Float.parseFloat(bean.getPingfen()));
        }
    }
}
