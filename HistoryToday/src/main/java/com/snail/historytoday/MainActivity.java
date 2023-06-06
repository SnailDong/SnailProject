package com.snail.historytoday;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.snail.historytoday.base.BaseActivity;
import com.snail.historytoday.base.ContentURL;
import com.snail.historytoday.bean.HistoryBean;
import com.snail.historytoday.bean.LaoHuangliBean;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ListView mainLv;
    private ImageButton imgBtn;
    List<HistoryBean.ResultDTO> mDatas;
    private HistoryAdapter adapter;

    private Calendar calendar;
    private Date date;
    HistoryBean historyBean;
    //声明头布局当中的TextView
    TextView yinliTv,dayTv,weekTv,yangliTv,baijiTv,wuxingTv,chongshaTv,jishenTv,xiongshenTv,yiTv,jiTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化主页面控件
        mainLv = (ListView) findViewById(R.id.main_lv);
        imgBtn = (ImageButton) findViewById(R.id.main_imgbtn);
        //日历图标点击弹出日历对话框
        imgBtn.setOnClickListener(this);

        //初始化数据源，初始化适配器
        mDatas = new ArrayList<>();
        adapter = new HistoryAdapter(this, mDatas);
        mainLv.setAdapter(adapter);

        //获取日历对象
        calendar = Calendar.getInstance();
        date = new Date();
        //初始化当日日期
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //添加头尾布局
        addHeaderAndFooterView();
        String date = month +"/"+day;
        //获取当日数据
        String todayHistoryURL = ContentURL.getTodayHistoryURL(date);
        loadData(todayHistoryURL);
        /* 因为ListView添加头布局了，所以position对应集合的位置发生变化，集合第0个数据，position为第1个数据，所以要减掉一个*/
        mainLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, HistoryDescActivity.class);
                HistoryBean.ResultDTO resultBean = mDatas.get(position-1);
                String bean_id = resultBean.geteId();
                intent.putExtra("hisId",bean_id);
                startActivity(intent);
            }
        });
    }

    private void addHeaderAndFooterView() {
        //给ListView添加头尾布局
        View headerView = LayoutInflater.from(this).inflate(R.layout.main_headerview,null);
        //初始化头布局控件
        initHeaderView(headerView);
        mainLv.addHeaderView(headerView);
        View footerView = LayoutInflater.from(this).inflate(R.layout.main_footer,null);
        //点击加载更多时通过tag判断
        footerView.setTag("footer");
        footerView.setOnClickListener(this);
        mainLv.addFooterView(footerView);
    }

    private void initHeaderView(View headerView) {
        /* 初始化ListView头布局当中的每一个控件*/
        yinliTv = headerView.findViewById(R.id.main_header_tv_nongli);
        dayTv = headerView.findViewById(R.id.main_header_tv_day);
        weekTv = headerView.findViewById(R.id.main_header_tv_week);
        yangliTv = headerView.findViewById(R.id.main_header_tv_yangli);
        baijiTv = headerView.findViewById(R.id.main_header_tv_baiji);
        wuxingTv = headerView.findViewById(R.id.main_header_tv_wuxing);
        chongshaTv = headerView.findViewById(R.id.main_header_tv_chongsha);
        jishenTv = headerView.findViewById(R.id.main_header_tv_jishen);
        xiongshenTv = headerView.findViewById(R.id.main_header_tv_xiongshen);
        yiTv = headerView.findViewById(R.id.main_header_tv_yi);
        jiTv = headerView.findViewById(R.id.main_header_tv_ji);
        //将日期对象转换成指定格式的字符串形式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        //头布局中有日期黄历显示，初始化加载当日黄历
        String laohuangliURL = ContentURL.getLaohuangliURL(time);
        loadHeaderData(laohuangliURL);
    }

    private void loadHeaderData(String laohuangliURL) {
        /* 获取老黄历接口的数据*/
        RequestParams params = new RequestParams(laohuangliURL);
        x.http().get(params, new CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LaoHuangliBean huangliBean = new Gson().fromJson(result, LaoHuangliBean.class);
                LaoHuangliBean.ResultDTO resultBean = huangliBean.getResult();
                yinliTv.setText("农历 "+resultBean.getYinli()+" (阴历)");
                String[] yangliArr = resultBean.getYangli().split("-");
                String week = getWeek(Integer.parseInt(yangliArr[0]), Integer.parseInt(yangliArr[1]), Integer.parseInt(yangliArr[2]));
                yangliTv.setText("公历 "+yangliArr[0]+"年"+yangliArr[1]+"月"+yangliArr[2]+"日 "+week+"(阳历)");

                dayTv.setText(yangliArr[2]);
                weekTv.setText(week);
                baijiTv.setText("彭祖百忌:"+resultBean.getBaiji());
                wuxingTv.setText("五行:"+resultBean.getWuxing());
                chongshaTv.setText("冲煞:"+resultBean.getChongsha());
                jishenTv.setText("吉神宜趋:"+resultBean.getJishen());
                xiongshenTv.setText("凶神宜忌:"+resultBean.getXiongshen());
                yiTv.setText("宜:"+resultBean.getYi());
                jiTv.setText("忌:"+resultBean.getJi());
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private String getWeek(int year, int month, int day) {
//        根据年月日获取对应的星期
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        String weeks[] = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        int index = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (index<0){
            index = 0;
        }
        return weeks[index];
    }

    @Override
    public void onClick(View view) {
        if (view.getId() ==  R.id.main_imgbtn) {
            popCalendarDialog();
            return;
        }

        String tag = (String) view.getTag();
        if (tag.equals("footer")) {
            Intent intent = new Intent(this, HistoryActivity.class);
            if (historyBean!=null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("history",historyBean);
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    private void popCalendarDialog() {
        //弹出日历对话框
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //改变老黄历显示的内容
                String time = year+"-"+(month+1)+"-"+dayOfMonth;
                String laohuangliURL = ContentURL.getLaohuangliURL(time);
                loadHeaderData(laohuangliURL);

                //改变历史上的今天数据
                String date = (month + 1) + "/" + dayOfMonth;
                String todayHistoryURL = ContentURL.getTodayHistoryURL(date);
                loadData(todayHistoryURL);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onSuccess(String result) {
        mDatas.clear();
        historyBean = new Gson().fromJson(result, HistoryBean.class);
        List<HistoryBean.ResultDTO> list = historyBean.getResult();
        for (int i = 0; i < 5; i++) {
            mDatas.add(list.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}