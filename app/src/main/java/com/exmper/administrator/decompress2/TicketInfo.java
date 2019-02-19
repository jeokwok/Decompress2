package com.exmper.administrator.decompress2;

import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2019/1/12.
 * 票信息逻辑
 */

public class TicketInfo {

    private String mFromStation;
    private TextView mToStation;
    private TextView mNum;

    public String getFromStation() {
        return mFromStation;
    }

    public void setFromStation(String fromStation) {
        mFromStation = fromStation;
    }

    private TextView mFromDate;
    private TextView mToDate;
    private TextView mSoftSet;
    private TextView mOneSet;
    private TextView mTwoSet;
    private TextView mPrice;



    public TextView getPrice() {
        return mPrice;
    }

    public void setPrice(TextView price) {
        mPrice = price;
    }

    public TextView getTwoSet() {
        return mTwoSet;
    }

    public void setTwoSet(TextView twoSet) {
        mTwoSet = twoSet;
    }

    public TextView getOneSet() {
        return mOneSet;
    }

    public void setOneSet(TextView oneSet) {
        mOneSet = oneSet;
    }

    public TextView getSoftSet() {
        return mSoftSet;
    }

    public void setSoftSet(TextView softSet) {
        mSoftSet = softSet;
    }

    public TextView getToDate() {
        return mToDate;
    }

    public void setToDate(TextView toDate) {
        mToDate = toDate;
    }

    public TextView getFromDate() {
        return mFromDate;
    }

    public void setFromDate(TextView fromDate) {
        mFromDate = fromDate;
    }

    public TextView getNum() {
        return mNum;
    }

    public void setNum(TextView num) {
        mNum = num;
    }

    public TextView getToStation() {
        return mToStation;
    }

    public void setToStation(TextView toStation) {
        mToStation = toStation;
    }

    /*******************
     private String map;
    private List<String> ticketInfo;

    public TicketInfo(String map, List<String> ticketInfo) {
        this.map = map;
        this.ticketInfo = ticketInfo;
    }

    public TicketInfo() {
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public List<String> getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(List<String> ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    @Override
    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer("data{");
        stringBuffer.append("map").append(map).append('\'');
        stringBuffer.append(",result[").append("|预订|").append(ticketInfo).append("|Y|").append('\'');
        //stringBuffer.append("|预订|").append(ticketInfo).append("|Y|");.append("null,")
        return stringBuffer.toString();
    }*****************/
}
