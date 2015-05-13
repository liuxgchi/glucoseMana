package com.common.library.customview;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 自定义viewpager
 *功能：１：传统功能
 * 　　２：监听左右滑动，当右滑３０％位移后开始通知滑动方向．解释一下向左滑，即出现右边的页面．：
 * Created by xing on 2015/4/9.
 */
public class SmartViewPager extends ViewPager {
    private boolean left = false;
    private boolean right = false;
    private boolean isScrolling = false;
    private int lastValue = -1;
    private ChangeListener changeListener = null;

    private boolean isDirectionCalled;

    public SmartViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartViewPager(Context context) {
        super(context);
        init();
    }

    public boolean isDirectionCalled() {
        return isDirectionCalled;
    }

    /**
     * 设置是否调用过了
     * @param isDirectionCalled
     */
    public void setDirectionCalled(boolean isDirectionCalled) {
        this.isDirectionCalled = isDirectionCalled;
    }

    /**
     * init method .
     */
    private void init() {
        setOnPageChangeListener(listener);
    }

    /**
     * listener ,to get move direction .
     */
    public OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 1) {
                isScrolling = true;
            } else {
                isScrolling = false;
            }

//            if (arg0 == 2) {
//                //notify ....
//                if(changeListener !=null){
//                    changeListener.onDirectionChange(left, right);
//                }
//                right = left = false;
//            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
//            arg0 :当前页面，及你点击滑动的页面
//            arg1:当前页面偏移的百分比
//            arg2:当前页面偏移的像素位置,　viewpager向右滑此越来越大，向左滑些值越来越小．注意此值一理滑动完就会变成０
//            LogUtil.outLogDetail("lastOffDis===="+lastValue);
//            LogUtil.outLogDetail("arg1====="+arg1);
//            LogUtil.outLogDetail("offDis====="+arg2);
            if (isScrolling && arg2 != 0 && lastValue !=0) {
                if (lastValue > arg2) {
                    // 递减，向右侧滑动
                    right = true;
                    left = false;
                } else if (lastValue < arg2) {
                    // 递减，向右侧滑动
                    right = false;
                    left = true;
                } /*else if (lastValue == arg2) {
                    right = left = false;
                }*/
                //３０％的位移就开始通知滑动方向．
//                if(arg1 > 0.30){
//                    if(changeListener !=null){
//                        if(!isDirectionCalled){
//                            changeListener.onDirectionChange(left, right);
//                        }
//                    }
//                }
            }
            lastValue = arg2;
        }

        @Override
        public void onPageSelected(int arg0) {
            if(changeListener !=null){
                changeListener.onPageSelected(arg0);
                if(!isDirectionCalled){
                    changeListener.onDirectionChange(left, right);
                }
            }
        }
    };

    /**
     *  滑动状态改变回调
     */
    public interface ChangeListener {

        /**
         * 切换视图 ？决定于left：手指向左滑．
         * @param left
         * @param right
         */
        public  void onDirectionChange(boolean left, boolean right);
        public  void  onPageSelected(int index);
    }

    /**
     * set ...
     * @param callback
     */
    public void setOnSmartPageChangedListener(ChangeListener callback){
        changeListener = callback;
    }
}
