package com.snail.historytoday.base;

public class ContentURL {

    //获取指定日期对应的历史上的今天的网址
    public static String getTodayHistoryURL(String date){
        String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php?date="+date+"&key=13a2a24fe2812553d21118e837f6aed0";
        return url;
    }

    //获取指定日期的老黄历的网址
    public static String getLaohuangliURL(String time){
        String url = "http://v.juhe.cn/laohuangli/d?date="+time+"&key=aa6d4c702325de6607eae66458fca99a";
        return url;
    }

    //根据指定事件id获取指定事件详情信息
    public static String getHistoryDescURL(String id){
        String url = "http://v.juhe.cn/todayOnhistory/queryDetail.php?"+ "key=13a2a24fe2812553d21118e837f6aed0&e_id=" + id;
        return url;
    }
}
