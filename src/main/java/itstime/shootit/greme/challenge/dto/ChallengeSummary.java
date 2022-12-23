package itstime.shootit.greme.challenge.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface ChallengeSummary {

    String getTitle();
    String getInfo();
    int getCur_num();
    Date getDeadline();

    /*
        추후 수정 예정(D-day부분)
     */

//    int getRes();

//    default int getRes() throws ParseException {
//
//        String todayFm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));  // 현재 날짜
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        Date date = new Date(String.valueOf(dateFormat.parse(String.valueOf(getDeadline().getTime()))));
//        Date today = new Date(String.valueOf(dateFormat.parse(todayFm)));
//
//        long calculate = date.getTime() - today.getTime();
//        int Days = (int) (calculate / (24*60*60*1000));
//
//        System.out.println(Days);
//        return Days;
//    }

}
