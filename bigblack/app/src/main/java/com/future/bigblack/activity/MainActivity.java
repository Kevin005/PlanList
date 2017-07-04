package com.future.bigblack.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.future.bigblack.R;
import com.future.bigblack.adapter.MyPlanAdapter;
import com.future.bigblack.bean.PlanInfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.future.bigblack.untils.DateUntil.dateToStamp;

public class MainActivity extends AppCompatActivity {
    private LinearLayout line_input_content;
    private EditText edt_input_content;
    private RadioButton rBtn_input_level;
    private ListView lv_content;
    private MyPlanAdapter adapter;

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
        edt_input_content = (EditText) findViewById(R.id.edt_input_content);
        rBtn_input_level = (RadioButton) findViewById(R.id.rBtn_input_level);
        lv_content = (ListView) findViewById(R.id.lv_content);
        adapter = new MyPlanAdapter(MainActivity.this);
        lv_content.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setData();
    }

    private void setData() {
        List<PlanInfo> infos = new ArrayList<>();
        PlanInfo info = new PlanInfo();
        info.setContent("今天去打猎");
        info.setIs_doing(1);
        info.setLevel(2);
        infos.add(info);

        PlanInfo info1 = new PlanInfo();
        info1.setContent("今天去打猎");
        info1.setIs_doing(1);
        info1.setLevel(1);
        infos.add(info1);

        PlanInfo info3 = new PlanInfo();
        info3.setContent("今天去打猎");
        info3.setIs_doing(1);
        info3.setLevel(1);
        infos.add(info3);

        PlanInfo info2 = new PlanInfo();
        info2.setContent("今天去打猎");
        info2.setIs_doing(0);
        info2.setLevel(1);
        infos.add(info2);

        PlanInfo info4 = new PlanInfo();
        info4.setContent("今天去打猎");
        info4.setIs_doing(1);
        info4.setLevel(2);
        infos.add(info4);

        PlanInfo info5 = new PlanInfo();
        info5.setContent("今天去打猎");
        info5.setIs_doing(1);
        info5.setLevel(1);
        infos.add(info5);

        PlanInfo info6 = new PlanInfo();
        info6.setContent("今天去打猎");
        info6.setIs_doing(1);
        info6.setLevel(1);
        infos.add(info6);

        PlanInfo info7 = new PlanInfo();
        info7.setContent("今天去打猎");
        info7.setIs_doing(0);
        info7.setLevel(1);
        infos.add(info7);
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
                //TODO post
            }
        });
        cal_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectDateStr = year + "-" + month + "-" + dayOfMonth + " 00:00:00";
                Log.e("abc", selectDateStr);
                try {
                    String selectStampStr = dateToStamp(selectDateStr);
                    Log.e("abc", selectStampStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
