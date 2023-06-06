package com.snail.historytoday;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snail.historytoday.bean.HistoryBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author uidq5232
 * @data 2023-6-6
 */
public class HistoryAdapter extends BaseAdapter {
    Context context;
    //定义数据源
    List<HistoryBean.ResultDTO> mDatas;

    public HistoryAdapter(Context context, List<HistoryBean.ResultDTO> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    //继承adapter
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_main_timeline,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        HistoryBean.ResultDTO resultDTO = mDatas.get(position);
        //判断当前位置的年份和上一个位置是否相同
        if (position!=0) {
            HistoryBean.ResultDTO lastBean = mDatas.get(position - 1);
            if (resultDTO.getDate().equals(lastBean.getDate())) {
                holder.timeLayout.setVisibility(View.GONE);
            }else {
                holder.timeLayout.setVisibility(View.VISIBLE);
            }
        }else {
            holder.timeLayout.setVisibility(View.VISIBLE);
        }

        holder.timeTv.setText(resultDTO.getDate());
        holder.titleTv.setText(resultDTO.getTitle());
        String picURL = "";
        if (TextUtils.isEmpty(picURL)) {
            holder.picIv.setVisibility(View.GONE);
        }else {
            holder.picIv.setVisibility(View.VISIBLE);
            Picasso.with(context).load(picURL).into(holder.picIv);
        }
        return view;
    }

    class ViewHolder{
        TextView timeTv,titleTv;
        ImageView picIv;
        LinearLayout timeLayout;
        public ViewHolder(View itemView){
            timeTv = itemView.findViewById(R.id.item_main_time);
            titleTv = itemView.findViewById(R.id.item_main_title);
            picIv = itemView.findViewById(R.id.item_main_pic);
            timeLayout = itemView.findViewById(R.id.item_main_ll);
        }
    }
}
