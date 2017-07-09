package com.future.bigblack.bean;

import com.future.bigblack.adapter.MyPlanAdapter;

/**
 * Created by kevin on 17-7-2.
 */

public class SelectedVewpagerInfo {
    private MyPlanAdapter selectedAdapter;
    private String selectedDay;//MM-YY-DD
    private PlanInfo selectedPlanInfo;

    public MyPlanAdapter getSelectedAdapter() {
        return selectedAdapter;
    }

    public void setSelectedAdapter(MyPlanAdapter selectedAdapter) {
        this.selectedAdapter = selectedAdapter;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public PlanInfo getSelectedPlanInfo() {
        return selectedPlanInfo;
    }

    public void setSelectedPlanInfo(PlanInfo selectedPlanInfo) {
        this.selectedPlanInfo = selectedPlanInfo;
    }

}
