package com.guaju.sugertea.adpter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by guaju on 2017/10/11.
 */

public class ShopDetailViewPagerAdaper extends PagerAdapter {
    ArrayList<View> lists;
    String[] tabNames;

    public ShopDetailViewPagerAdaper(ArrayList<View> lists,String[] tabNames) {
        this.lists = lists;
        this.tabNames=tabNames;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View recyclerView = lists.get(position);
        ViewGroup parent = (ViewGroup) recyclerView.getParent();
        if (parent!=null){
            parent.removeView(recyclerView);
        }
        container.addView(recyclerView);

        return recyclerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabNames[position];
    }
}
