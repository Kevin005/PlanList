# PlanList
这是一个**Android**应用程序，有着极方便、简洁、智能的清单功能。

```
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
                Log.e("abc", year + "====" + month + "====" + dayOfMonth);
            }
        });
    }


<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <CalendarView
        android:id="@+id/cal_view"
        android:layout_width="300dip"
        android:layout_height="310dip"
        android:background="@color/whitesmoke" />
</LinearLayout>


<style name="dialog" parent="@android:style/Theme.Dialog">
        <item name="android:windowFrame">@null</item>
        <item name="android:windowIsFloating">true</item>
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowNoTitle">true</item>
        <item name="android:background">@android:color/transparent</item>
        <item name="android:windowBackground">@android:color/transparent</item>
        <item name="android:backgroundDimEnabled">true</item>
        <item name="android:backgroundDimAmount">0.3</item>
    </style>
```
