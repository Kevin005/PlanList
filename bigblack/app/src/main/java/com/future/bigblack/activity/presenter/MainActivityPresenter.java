package com.future.bigblack.activity.presenter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.future.bigblack.R;
import com.future.bigblack.activity.Server.MainActivityInterface;

/**
 * Created by kevin on 17-7-16.
 */

public class MainActivityPresenter {
    private Context context;

    public MainActivityPresenter(Context context) {
        this.context = context;
    }

    public void showRightPop(View flowView, final MainActivityInterface.OnRightPopListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_popup, null);
        //实例化一个PopupWindow（加载的布局，视图的宽度，视图的高度，是否可见）
        final PopupWindow mPopupwindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置PopupWindow的背景颜色
        mPopupwindow.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        TextView tvPopBigPlan = (TextView) view.findViewById(R.id.tv_pop_big_plan);
        tvPopBigPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBigPlan();
                mPopupwindow.dismiss();
            }
        });
        TextView tvPopSearch = (TextView) view.findViewById(R.id.tv_pop_search);
        tvPopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSearch();
                mPopupwindow.dismiss();
            }
        });
        //设置PopupWindow的位置（挂载的控件，X轴的偏移量，Y轴的偏移量）
        mPopupwindow.showAsDropDown(flowView, 0, 20);
    }

}
