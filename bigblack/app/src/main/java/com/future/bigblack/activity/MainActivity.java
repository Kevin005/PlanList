package com.future.bigblack.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.future.bigblack.R;
import com.future.bigblack.adapter.MyPlanAdapter;
import com.future.bigblack.bean.PlanInfo;
import com.future.bigblack.database.PlanInfoDBHelper;
import com.future.bigblack.untils.DateUntil;
import com.future.bigblack.untils.PopOptionUtil;

import java.util.ArrayList;
import java.util.List;

import static com.future.bigblack.untils.DateUntil.getCurrentYMD;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getName();
    private LinearLayout line_input_content;
    private EditText edt_input_content;
    private CheckBox cBox_input_level;
    //    private ListView lv_content;
//    private MyPlanAdapter adapter;
    private PopOptionUtil mPop;
    private FloatingActionButton fab;
    private TextView tv_today;

    private PlanInfo selectInfo;
    private MyPlanAdapter selectAdapter;
    private List<String> currentWeekDays;//本周发计划天集合
    private String selectDateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog();
            }
        });
        line_input_content = (LinearLayout) findViewById(R.id.line_input_content);
        line_input_content.setVisibility(View.GONE);
        edt_input_content = (EditText) findViewById(R.id.edt_input_content);
        cBox_input_level = (CheckBox) findViewById(R.id.cBox_input_level);
//        lv_content = (ListView) findViewById(R.id.lv_content);
        initPopOption();
//        adapter = new MyPlanAdapter(MainActivity.this, new MyPlanAdapter.MyPlanAdapterCallback() {
//            @Override
//            public void tvContentLongClick(View selectView) {
//                if (mPop != null) mPop.show(selectView);
//            }
//
//            @Override
//            public void selectInfo(PlanInfo selectInfo) {
//                MainActivity.this.selectInfo = selectInfo;
//            }
//        });
//        lv_content.setAdapter(adapter);
//        lv_content.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                line_input_content.setVisibility(View.GONE);
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(edt_input_content.getWindowToken(), 0);
//                return false;
//            }
//        });
        selectDateStr = getCurrentYMD();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (line_input_content.getVisibility() == View.VISIBLE) {
                    if (!TextUtils.isEmpty(edt_input_content.getText().toString())) {
                        sendPlan();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edt_input_content.getWindowToken(), 0);
                        line_input_content.setVisibility(View.GONE);
                        edt_input_content.setText("");
                        cBox_input_level.setChecked(false);
                        selectAdapter.refreshData();
                    }
                } else {
                    showSoftInputFromWindow(MainActivity.this, edt_input_content);
                    edt_input_content.setText("");
                    cBox_input_level.setChecked(false);
                    line_input_content.setVisibility(View.VISIBLE);
                }
            }
        });
        initCurrentWeekDays();
        initListView();
        ViewPager vPager_main = (ViewPager) findViewById(R.id.vPager_main);
        PagerViewAdapter viewPagerAdapter = new PagerViewAdapter();
        vPager_main.setAdapter(viewPagerAdapter);
        vPager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, position + "====" + positionOffset + "===" + positionOffsetPixels);
                selectAdapter = adapters.get(position);
                selectDateStr = selectAdapter.getSelectDay();
                if (selectDateStr.equals(getCurrentYMD())) {
                    tv_today.setText(selectDateStr + "今天");
                } else {
                    tv_today.setText(selectDateStr);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, position + "");
                selectAdapter = adapters.get(position);
                selectDateStr = selectAdapter.getSelectDay();
                selectAdapter.refreshData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, state + "");
            }
        });
        vPager_main.setCurrentItem(currentWeekDays.size());
    }

    private void sendPlan() {
        String edtStr = edt_input_content.getText().toString();
//        if (!TextUtils.isEmpty(edtStr)) {
//            String[] contents = new String[]{};
//            if (edtStr.contains("。。")) {
//                int count = 0;
//                for (int i = 0; i < edtStr.length(); i++) {
//                    if (edtStr.charAt(i) == '。') {
//                        count++;
//                    }
//                }
//                if (count == 2) {
//                    contents = edtStr.split("。。");
//                }
//            } else if (edtStr.contains("..")) {
//                int count = 0;
//                for (int i = 0; i < edtStr.length(); i++) {
//                    if (edtStr.charAt(i) == '.') {
//                        count++;
//                    }
//                }
//                if (count == 2) {
//                    contents = edtStr.split("..");
//                }
//            }
//            if (contents.length > 0) {
//                List<PlanInfo> infos = new ArrayList<>();
//                for (int i = 0; i < contents.length; i++) {
//                    PlanInfo info = new PlanInfo();
//                    info.setContent(contents[i]);
//                    info.setIs_doing(1);
//                    info.setLevel(getLevel());
//                    if (getCurrentYMD().equals(selectDateStr)) {
//                        info.setDateDay(getCurrentYMD());
//                    } else {
//                        info.setDateDay(selectDateStr);
//                    }
//                    info.setDateStamp(System.currentTimeMillis());
//                    infos.add(info);
//                }
//                PlanInfoDBHelper.insertInfo(infos, MainActivity.this);
//            } else {
                PlanInfo info = new PlanInfo();
                info.setContent(edtStr);
                info.setIs_doing(1);
                info.setLevel(getLevel());
                if (getCurrentYMD().equals(selectDateStr)) {
                    info.setDateDay(getCurrentYMD());
                } else {
                    info.setDateDay(selectDateStr);
                }
                info.setDateStamp(System.currentTimeMillis());
                PlanInfoDBHelper.insertInfo(info, MainActivity.this);
//            }
//        }
    }


    private void initCurrentWeekDays() {
        currentWeekDays = PlanInfoDBHelper.getWeekDays(DateUntil.getMondayOfThisWeek(), DateUntil.getSundayOfThisWeek(), MainActivity.this);
        String todayStr = DateUntil.getCurrentYMD();
        boolean today_post = false;
        for (String today : currentWeekDays) {
            if (todayStr.equals(today)) {//今天是否有发过计划
                today_post = true;
            }
        }
        if (!today_post) {//如果没有发过
            currentWeekDays.add(todayStr);//加入一条初始空白数据
        }
    }

    private List<ListView> listViews;
    private List<MyPlanAdapter> adapters;

    private void initListView() {
        listViews = new ArrayList<ListView>();
        adapters = new ArrayList<MyPlanAdapter>();
        for (int i = 0; i < currentWeekDays.size(); i++) {
            ListView listView = (ListView) getLayoutInflater().inflate(R.layout.view_main_list, null);

            MyPlanAdapter adapter = new MyPlanAdapter(MainActivity.this, currentWeekDays.get(i), new MyPlanAdapter.MyPlanAdapterCallback() {
                @Override
                public void tvContentLongClick(View selectView) {
                    if (mPop != null) mPop.show(selectView);
                }

                @Override
                public void selectInfo(PlanInfo selectInfo) {
                    MainActivity.this.selectInfo = selectInfo;
                }
            });
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    line_input_content.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_input_content.getWindowToken(), 0);
                    return false;
                }
            });
            adapter.refreshData();
            listView.setAdapter(adapter);
            listViews.add(listView);
            adapters.add(adapter);
        }
    }

    public class PagerViewAdapter extends PagerAdapter {


        public PagerViewAdapter() {
        }

        @Override
        public int getCount() {
            return listViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(listViews.get(position));
            return listViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(listViews.get(position));
        }
    }

    private void initPopOption() {
        mPop = new PopOptionUtil(MainActivity.this);
        mPop.setOnPopClickEvent(new PopOptionUtil.PopClickEvent() {
            @Override
            public void onCopy() {
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(edt_input_content.getText());
                Snackbar.make(fab, "复制成功", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                mPop.dismiss();
            }

            @Override
            public void onEdit() {
                if (selectInfo != null) {
                    edt_input_content.setText(selectInfo.getContent());
                    if (selectInfo.getLevel() == 2) {
                        cBox_input_level.setChecked(true);
                    } else {
                        cBox_input_level.setChecked(false);
                    }
                    showSoftInputFromWindow(MainActivity.this, edt_input_content);
                    line_input_content.setVisibility(View.VISIBLE);
                }
                mPop.dismiss();
            }

            @Override
            public void onDelete() {
                if (selectInfo != null) {
                    PlanInfoDBHelper.deleteInfoById(selectInfo.getId(), MainActivity.this);
                    if (selectAdapter != null) {
                        selectAdapter.refreshData();
                    }
                }
                mPop.dismiss();
            }

            @Override
            public void onSetTop() {
                mPop.dismiss();
            }
        });
    }

    //弹出软件盘
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    private int getLevel() {
        if (cBox_input_level.isChecked()) {
            return 2;
        }
        return 1;
    }

    private void getData(String dayDate) {
        List<PlanInfo> infos = PlanInfoDBHelper.getOneDayInfos(dayDate, MainActivity.this);
        selectAdapter.setData(infos);
    }

    private void popDialog() {
        View view_dialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_calendar, null);
        CalendarView cal_view = (CalendarView) view_dialog.findViewById(R.id.cal_view);
        LinearLayout line_cal = (LinearLayout) view_dialog.findViewById(R.id.line_cal);
        final Dialog build2 = new Dialog(MainActivity.this, R.style.dialog);
        build2.setContentView(view_dialog);
        build2.setCanceledOnTouchOutside(true);
        build2.show();
        build2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        line_cal.getBackground().setAlpha(200);
        cal_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectDateStr = year + "-" + String.format("%0" + 2 + "d", (month + 1)) + "-" + String.format("%0" + 2 + "d", dayOfMonth);
                getData(selectDateStr);
            }
        });
    }


}
