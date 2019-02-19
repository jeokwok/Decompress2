package com.exmper.administrator.decompress2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 60411 on 2019/2/19.
 * TicketInfo 工具类
 * 用于显示票信息
 */

public class TicketInfoUtils {
    private String mString;

    public TicketInfoUtils(String string) {
        mString = string;
    }

    public List<TicketInfo> getTicketInfo(){
        List<TicketInfo> mTicketInfo = new ArrayList<>();
        TicketInfo ticket = new TicketInfo();
        //
      ticket.setFromStation(mString);

        mTicketInfo.add(ticket);
        return mTicketInfo;

    }
}
