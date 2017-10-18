package com.guaju.sugertea.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.guaju.sugertea.R;

//stickya粘性 'stiki
public class StickyLayout extends LinearLayout {
    private static final String TAG = "StickyLayout";
    private View mTop;
    private View mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;
    private View mInnerScrollView;
    private boolean isTopHidden = false;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    private boolean isInControl = false;
    //自定义布局的构造方法,在布局声明的时候自动创建
    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置垂直排列方式
        setOrientation(LinearLayout.VERTICAL);
        //android 自带控件
        mScroller = new OverScroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //判断手指是滑动还是触摸的临界值
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        //拿到控件fling 自动滑动的最大速度
        mMaximumVelocity = viewConfiguration
                .getScaledMaximumFlingVelocity();
        //对应于上面的最小速度
        mMinimumVelocity = viewConfiguration
                .getScaledMinimumFlingVelocity();

    }

    //在界面渲染完成之后才会触发这个操作
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //指定的一些规则，用户要按照规则使用控件
        mTop = findViewById(R.id.ll_head); //头部布局id
        mNav = findViewById(R.id.tablayout);//menu 的id
        View view = findViewById(R.id.vp);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException(
                    "id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
    }

    //因为布局当中使用了viewpager嵌套scrollview,
    // 如果让scrollview嵌套viewpager 那么viewpager高度就自动变成零
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得viewpager的布局参数，因为scrollview嵌套viewpager的时候viewpager的测量模式是未指定unspecified
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        //让viewpager的高度等于 当前控件的高度减去 菜单条的高度
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        //在onmeasure不需要再次给viewpager设置一遍layoutparams
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //再次测量一下顶部高度
        mTopViewHeight = mTop.getMeasuredHeight();
    }
    //分发事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //拿到当前手指的操作
        int action = ev.getAction();
        //记录一下当前手指的y轴坐标
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //y值会随着手指滑动一直发生变化
                float dy = y - mLastY; //dy就是手指滑动的距离，
                // dy<0的时候说明在往上滑，dy>0时往下滑
                getCurrentScrollView();
                //如果是ScrollView
                if (mInnerScrollView instanceof ScrollView) {
                    //判断scroll的滑动距离是0 并且 顶部view是隐藏状态 并且 手指在往上滑
                    if (mInnerScrollView.getScrollY() == 0 && isTopHidden && dy > 0
                            && !isInControl) {
                        isInControl = true;
                        //让当前的触摸事件取消：即不分发，不做任何操作
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        //又重新创建了一个触摸事件
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        //又重新执行一遍分发事件
                        dispatchTouchEvent(ev);//因为此时ev已经是  MotionEvent.ACTION_CANCEL 所以就直接退出了
                        //让当前的ev2事件的事件再置为按下操作
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2); //就执行了一个按下分发操作
                    }
                } else if (mInnerScrollView instanceof ListView) {

                    ListView lv = (ListView) mInnerScrollView;
                    //拿到lv第一个可见条目
                    View c = lv.getChildAt(lv.getFirstVisiblePosition());
//                    c.getTop() == 0条目的第一条并且顶部正好是viewpager的顶部，正好是在开始的位置

                    if (!isInControl && c != null && c.getTop() == 0 && isTopHidden
                            && dy > 0) {
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                }else if (mInnerScrollView instanceof RecyclerView) {

                    RecyclerView rv = (RecyclerView) mInnerScrollView;
//                    android.support.v4.view.ViewCompat.canScrollVertically(rv, -1)判断recyclerview是否在顶部
//                      如果是1的话就是判断是否到底部
                    if (!isInControl && android.support.v4.view.ViewCompat.canScrollVertically(rv, -1) && isTopHidden
                            && dy > 0) {
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);
                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     *   拦截事件，当不允许里面的滑动布局滑动的话就必须要拦截，必须要返回true
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                //if  dy>0 往下滑  if  dy<0 往上滑
                getCurrentScrollView();
                //判断是否滑动还是触摸，大于的话说明是滑动，小于说明是触摸
                if (Math.abs(dy) > mTouchSlop) {
                    mDragging = true;  //标志着我当前是滑动
                    if (mInnerScrollView instanceof ScrollView) {
                        // 如果topView没有隐藏
                        // 或sc的scrollY = 0 && topView隐藏 && 下拉，则拦截
                        if (!isTopHidden
                                || (mInnerScrollView.getScrollY() == 0
                                && isTopHidden && dy > 0)) {
                            //初始化速度跟踪器如果跟踪器不存在的话
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y; //判断是否是往上滑了
                            return true;
                        }
                    } else if (mInnerScrollView instanceof ListView) {

                        ListView lv = (ListView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        // 如果topView没有隐藏
                        // 或sc的listView在顶部 && topView隐藏 && 下拉，则拦截

                        if (!isTopHidden || //
                                (c != null //
                                        && c.getTop() == 0//
                                        && isTopHidden && dy > 0)) {

                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }else if (mInnerScrollView instanceof RecyclerView) {
                        RecyclerView rv = (RecyclerView) mInnerScrollView;
                        if (!isTopHidden || (!android.support.v4.view.ViewCompat.canScrollVertically(rv, -1) && isTopHidden && dy > 0)) {
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }
                    else if (mInnerScrollView instanceof FrameLayout) {
                        //新添加功能能够在viewpager中添加view
                        return  true;
                    }

                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        //时刻记住自己是linearylayout 滚动会传送下去
        return super.onInterceptTouchEvent(ev);
    }
    //拿到正在显示在界面中的scrollview
    private void getCurrentScrollView() {
        //拿到当前的页面 如果有三页  就是0 1 2
        int currentItem = mViewPager.getCurrentItem();
        //拿到当前的viewPager的适配器
        PagerAdapter a = mViewPager.getAdapter();
        //通过适配器和当前页面，拿到viewpager下方的控件
        mInnerScrollView = (View) a.instantiateItem(mViewPager, currentItem);


//        if (a instanceof FragmentPagerAdapter) {
//            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
//            Fragment item = (Fragment) fadapter.instantiateItem(mViewPager,
//                    currentItem);
//            mInnerScrollView = (ViewGroup) (item.getView()
//                    .findViewById(R.id.id_stickynavlayout_innerscrollview));
//        } else if (a instanceof FragmentStatePagerAdapter) {
//            FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
//            Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager,
//                    currentItem);
//            mInnerScrollView = (ViewGroup) (item.getView()
//                    .findViewById(R.id.id_stickynavlayout_innerscrollview));
//        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //速度跟踪器的创建
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);

        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //如果scroller没有结束了，那么就取消动画
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;

                Log.e("TAG", "dy = " + dy + " , y = " + y + " , mLastY = " + mLastY);

                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }
                if (mDragging) {
//                    scrollTo(int x,int y);滚动到这个位置
                    scrollBy(0, (int) -dy);//滚动一个距离

                    // 如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                    if (getScrollY() == mTopViewHeight && dy < 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        //接着分发这个事件（这个时候y值是当前的y值，而不是最早那个）
                        dispatchTouchEvent(event);
                        //当不让linearylayout滚动 的时候 把这个设为false
                        isInControl = false;
                    }
                }
                mLastY = y;   //必须要赋值，scrollby是按照手指的滑动去滚得
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                //计算当前的速度
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();  //通过跟踪器拿到的y轴方向的速度
                Log.e(TAG, "onTouchEvent: "+velocityY );
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);   //执行手指释放是的惯性滑动的操作
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }
    //自动滑动
    public void fling(int velocityY) {
        //滑翔
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate(); //更新界面
    }
     //TODO 什么时候调用
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        //设置是否是隐藏
        isTopHidden = getScrollY() == mTopViewHeight;

    }
    //TODO  什么时候调用
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
    //创建速度跟踪器
    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }
    //速度跟踪器占用资源有些大，需要及时释放，重置
    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

}
