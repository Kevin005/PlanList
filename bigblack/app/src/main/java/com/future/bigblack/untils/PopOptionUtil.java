package com.future.bigblack.untils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.future.bigblack.R;

/**
 * Created by Kevin on 2017/7/6.
 */

public class PopOptionUtil {
    private Context mContext;
    private int popupWidth;
    private int popupHeight;
    private PopupWindow popupWindow;
    private PopClickEvent mEvent;
    private Button btnPopCopy;
    private Button btnPopEdit;
    private Button btnPopDel;
    private Button btnPopSetTop;

    public PopOptionUtil(Context context) {
        mContext = context;
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.view_pop_option, null);
        btnPopCopy = (Button) popupView.findViewById(R.id.btn_pop_copy);
        btnPopEdit = (Button) popupView.findViewById(R.id.btn_pop_edit);
        btnPopDel = (Button) popupView.findViewById(R.id.btn_pop_del);
        btnPopSetTop = (Button) popupView.findViewById(R.id.btn_pop_set_top);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = popupView.getMeasuredWidth();
        popupHeight = popupView.getMeasuredHeight();
    }

    public void show(View view) {
        initEvent();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2,
                location[1] - popupHeight);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public interface PopClickEvent {
        void onCopy();

        void onEdit();

        void onDelete();

        void onSetTop();
    }

    public void setOnPopClickEvent(PopClickEvent mEvent) {
        this.mEvent = mEvent;
    }

    private void initEvent() {
        if (mEvent != null) {
            btnPopCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onCopy();
                }
            });
            btnPopEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onEdit();
                }
            });
            btnPopDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onDelete();
                }
            });
            btnPopSetTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEvent.onSetTop();
                }
            });
        }
    }
}
