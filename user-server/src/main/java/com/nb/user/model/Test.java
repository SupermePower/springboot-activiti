package com.nb.user.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description
 * @author: fly
 * @date: 2018/12/18 16:51
 */
public class Test {

    public static void main(String[] args) throws Exception {
        List list = new ArrayList();
        list.add("Tom");
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Order order = (Order) iterator.next();
            System.out.println();
        }
    }

    public static String getDealLine() {
        return "2019-9-9";
    }


    private static List<Stalls> getStalls() {
        List<Stalls> stallsList = new ArrayList<>();
//        Stalls stalls1 = new Stalls("A3");
//        Stalls stalls2 = new Stalls("C1");
//        Stalls stalls3 = new Stalls("A2");
//        Stalls stalls4 = new Stalls("B1");
//        Stalls stalls5 = new Stalls("A1");
//        stallsList.add(stalls1);
//        stallsList.add(stalls2);
//        stallsList.add(stalls3);
//        stallsList.add(stalls4);
//        stallsList.add(stalls5);
        return stallsList;
    }

    private static Optional<Timestamp> getTime(String examineTime) {
        if (examineTime == null) {
            return null;
        }
        String hour = examineTime.split(":")[0];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        try {
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            parse.setHours(Integer.parseInt(hour));
            return Optional.of(new Timestamp(parse.getTime()));
        } catch (ParseException e) {
            return Optional.of(null);
        }
    }
}