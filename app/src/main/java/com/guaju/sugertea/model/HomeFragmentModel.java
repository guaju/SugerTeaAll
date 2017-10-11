package com.guaju.sugertea.model;

import android.widget.ImageView;

import com.guaju.sugertea.R;
import com.guaju.sugertea.model.bean.TuijianShopBean;

import java.util.ArrayList;

/**
 * Created by guaju on 2017/9/18.
 */

public class HomeFragmentModel {

    //每八条创建新的集合
    public static ArrayList<TuijianShopBean.ListBean> generateNewList(ArrayList<TuijianShopBean.ListBean> lists, int page) {
        ArrayList<TuijianShopBean.ListBean> listBeen = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (8 * page + i <= lists.size()) {
                listBeen.add(lists.get(8 * page + i));
            }
        }
        return listBeen;
    }

    public static void resetViewPagerIndi(ImageView vp1, ImageView vp2) {
        vp1.setBackgroundResource(R.drawable.viewpager_normal);
        vp2.setBackgroundResource(R.drawable.viewpager_normal);
    }
}
