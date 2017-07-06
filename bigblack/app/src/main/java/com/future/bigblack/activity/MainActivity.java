package com.future.bigblack.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout line_input_content;
    private EditText edt_input_content;
    private CheckBox cBox_input_level;
    private ListView lv_content;
    private MyPlanAdapter adapter;
    private PopOptionUtil mPop;
    private FloatingActionButton fab;

    private PlanInfo selectInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv_today = (TextView) findViewById(R.id.tv_today);
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
        lv_content = (ListView) findViewById(R.id.lv_content);
        initPopOption();
        adapter = new MyPlanAdapter(MainActivity.this, new MyPlanAdapter.MyPlanAdapterCallback() {
            @Override
            public void tvContentLongClick(View selectView) {
                if (mPop != null) mPop.show(selectView);
            }

            @Override
            public void selectInfo(PlanInfo selectInfo) {
                MainActivity.this.selectInfo = selectInfo;
            }
        });
        lv_content.setAdapter(adapter);
        lv_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                line_input_content.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_input_content.getWindowToken(), 0);
                return false;
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (line_input_content.getVisibility() == View.VISIBLE) {
                    if (!TextUtils.isEmpty(edt_input_content.getText().toString())) {
                        PlanInfo info = new PlanInfo();
                        info.setContent(edt_input_content.getText().toString());
                        info.setIs_doing(1);
                        info.setLevel(getLevel());
                        info.setDateDay(DateUntil.getCurrentYMD());
                        info.setDateStamp(System.currentTimeMillis());
                        PlanInfoDBHelper.insertInfo(info, MainActivity.this);

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edt_input_content.getWindowToken(), 0);
                        line_input_content.setVisibility(View.GONE);
                        edt_input_content.setText("");
                        cBox_input_level.setChecked(false);
                        adapter.refreshData();
                    }
                } else {
                    showSoftInputFromWindow(MainActivity.this, edt_input_content);
                    edt_input_content.setText("");
                    cBox_input_level.setChecked(false);
                    line_input_content.setVisibility(View.VISIBLE);
                }

            }
        });
        getData(DateUntil.getCurrentYMD());
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
                    adapter.refreshData();
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
        adapter.setData(infos);
    }

    private void popDialog() {
        View view_dialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_calendar, null);
        CalendarView cal_view = (CalendarView) view_dialog.findViewById(R.id.cal_view);
        final Dialog build2 = new Dialog(MainActivity.this, R.style.dialog);
        build2.setContentView(view_dialog);
        build2.setCanceledOnTouchOutside(true);
        build2.show();
        build2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        cal_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectDateStr = year + "-" + String.format("%0" + 2 + "d", (month + 1)) + "-" + String.format("%0" + 2 + "d", dayOfMonth);
                getData(selectDateStr);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
