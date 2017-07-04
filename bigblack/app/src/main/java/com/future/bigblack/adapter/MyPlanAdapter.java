package com.future.bigblack.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.bigblack.R;
import com.future.bigblack.bean.PlanInfo;

import java.util.ArrayList;
import java.util.List;

public class MeFansAdapter extends BaseAdapter {

    private List<PlanInfo> data;
    private LayoutInflater inflater;
    private Context context;

    public MeFansAdapter(Context c) {
        context = c;
        inflater = LayoutInflater.from(context);
        data = new ArrayList<PlanInfo>();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PlanInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<PlanInfo> infos) {
        if (data == null) {
            data = new ArrayList<PlanInfo>();
        } else {
            data.clear();
        }
        data.addAll(infos);
        notifyDataSetChanged();
    }

    public void addData(List<PlanInfo> infos) {
        if (data == null) {
            data = new ArrayList<PlanInfo>();
        }
        data.addAll(infos);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final PlanInfo item = data.get(position);
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_plan_content, null);
            holder.cbox_is = (CheckBox) convertView.findViewById(R.id.user_icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.line_people = (LinearLayout) convertView.findViewById(R.id.line_people);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(item.getNick_name());
        holder.line_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonalDetailsActivity.class);
                intent.putExtra("user_id", item.getUser_id());
                intent.putExtra("user_name", item.getUser_name());
                intent.putExtra("nick_name", item.getNick_name());
                intent.putExtra("user_image", item.getImage());
                context.getCurrentActivity().startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public CheckBox user_icon;
        public TextView name;
        public LinearLayout line_people;
    }
}
