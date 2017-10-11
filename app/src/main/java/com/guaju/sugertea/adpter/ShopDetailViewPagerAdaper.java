package com.guaju.sugertea.adpter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by guaju on 2017/10/11.
 */

public class ShopDetailViewPagerAdaper extends PagerAdapter {
    ArrayList<ImageView> lists;

    public ShopDetailViewPagerAdaper(ArrayList<ImageView> lists) {
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = lists.get(position);
        ViewGroup parent = (ViewGroup) imageView.getParent();
        if (parent!=null){
            parent.removeView(imageView);
        }
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "服务项目";
    }
}
