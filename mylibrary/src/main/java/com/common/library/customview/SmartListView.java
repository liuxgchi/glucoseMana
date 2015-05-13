package com.common.library.customview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.common.library.R;
import com.common.library.util.ToastUtil;


/**
 * 自定义listview
 * fucntion: 1:分页
 * 2:
 * Created by xing on 2015/4/13.
 */
public class SmartListView extends ListView implements AbsListView.OnScrollListener {

    // ListView底部View
    private View moreView;
    private Handler handler;
    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;
    // 最后可见条目的索引
    private int lastVisibleIndex;
    private Context mContext;

    private int pageIndex = 1;

    private PageNationListener pageNationListener;

    private boolean isLastRow;

    /**
     * 总共要加载的数据条数
     */
    private int mTotalCount;

    /**
     * 总页数
     */
    private int mTotalPage;


    public void addFooterView(){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        moreView = inflater.inflate(R.layout.item_loading_more,null);
//        addFooterView(moreView,null,false);
        addFooterView(moreView);
    }

    public SmartListView(Context context) {
        super(context);
        this.mContext = context;

        setOnScrollListener(this);
        addFooterView();
    }

    public SmartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOnScrollListener(this);
        addFooterView();
    }

    public void setPageInfo(int totalCount,int totalPage) {
        this.mTotalCount = totalCount;
        this.mTotalPage = totalPage;
    }

    public void setPageNationListener(PageNationListener pageNationListener) {
        this.pageNationListener = pageNationListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
                //回调顺序如下
        //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
        //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
        //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
        //当滚到最后一行且停止滚动时，执行加载
        if (isLastRow && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            /**
             * pageIndex
             */
        if (pageIndex < mTotalPage){
            pageIndex = pageIndex + 1;
            pageNationListener.loadNextPage(pageIndex);
            }
            isLastRow = false;
        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
        //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
        //visibleItemCount：当前能看见的列表项个数（小半个也算）
        //totalItemCount：列表项共数

        //判断是否滚到最后一行
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
            /**所有的条目已经和最大条数相等，则移除底部的View
             * 注意
             * 1:totalItemCount 的值在快速滑动的时候会比listview 装载的全部数据多一个.
             */
        try{
            if (totalItemCount-getHeaderViewsCount() == mTotalCount || totalItemCount-getHeaderViewsCount() == mTotalCount+1 && mTotalCount !=0) {
                removeLoadingView();
                if (pageIndex != 1){
                    ToastUtil.showToast(mContext, "没有更多数据了！");
                }
                isLastRow = false;
            }else{
                isLastRow = true;
            }
        }catch (Exception e){

        }

        }
    }

    /**
     * 加载下一页数据(加载条显示)
     * 当加载完毕或者加载失败后要调用此方法
     */
    public void removeLoadingView(){
//        if (moreView != null){
//            if(getFooterViewsCount() > 0){
//                removeFooterView(moreView);
//            }
//        }
        if (moreView != null){
            moreView.setVisibility(View.GONE);
        }
    }

    public interface PageNationListener {
        /**
         *
         * @param loaddingPageIndex 将要加载的页数
         */
        public void loadNextPage(int loaddingPageIndex);
    }

}
