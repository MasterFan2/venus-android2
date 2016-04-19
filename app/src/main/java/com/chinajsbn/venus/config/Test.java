package com.chinajsbn.venus.config;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by MasterFan on 2016/4/19 12:00.
 * <p/>
 * description:
 */
public class Test {
    public static void main(String[] arg) {
        Locale.setDefault(Locale.US);//设置星期天显示方式【星期一第一天/星期天第一天】
        Calendar calendar = Calendar.getInstance();//初始化日历对象
        calendar.setLenient(true);//指定日期/时间解释是否是宽松的
        int today = calendar.get(Calendar.DAY_OF_MONTH);//当天
        int month = calendar.get(Calendar.MONTH);       //当月
        int firstDayOfWeek = calendar.getFirstDayOfWeek();//每周从周几开始
        calendar.set(Calendar.DAY_OF_MONTH, 1);           //设置第一天
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
        String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
        for (int i = 1; i < 8; i++) {
            System.out.printf("%4s", weekdayNames[i]);
        }
        System.out.println();
        do {
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day == 1) {
                System.out.print(" ");
                for (int i = 0; i < firstDay - 1; i++) {
                    System.out.print("    ");
                }
            }
            System.out.printf("%3d", day);
            if (day == today) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            if (calendar.get(Calendar.DAY_OF_WEEK) == firstDayOfWeek) {
                System.out.println();
                System.out.print(" ");
            }
        } while (calendar.get(Calendar.MONTH) == month);
    }
}
