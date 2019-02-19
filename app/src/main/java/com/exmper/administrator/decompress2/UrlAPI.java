package com.exmper.administrator.decompress2;

/**
 * Created by Administrator on 2019/1/7.
 */

public class UrlAPI {
    private static String URL_HEAD = "https://kyfw.12306.cn/otn/leftTicket/queryZ?";
    private static String URL_HTTP = "https://kyfw.12306.cn/otn/leftTicket/queryZ?" +
            "leftTicketDTO.train_date=2019-01-05&leftTicketDTO.from_station=SHH&" +
            "leftTicketDTO.to_station=HZH&purpose_codes=ADULT ";
    //String date;
    //String from_cityID;
    //String to_cityID;
     //private String quesy = "https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station="+from_cityID+"&leftTicketDTO.to_station="+to_cityID+"&purpose_codes=ADULT";
    public static String getUrlHead() {
        return URL_HEAD;
    }
    public static String getUrlHttp() {
        return URL_HTTP;
    }
    /*查票URL 获取方法*/
    public String getUrlAPI(String from_city,String to_city,String date){
       // this.date = date;
       // this.from_cityID = from_city;
        //this.to_cityID = to_city;
        String quesy = "https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date="+date+"&leftTicketDTO.from_station="+from_city+"&leftTicketDTO.to_station="+to_city+"&purpose_codes=ADULT";
        return quesy;
    }
}
