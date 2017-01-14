package com.rubik.chatme.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiennguyen on 1/14/17.
 */

public class DateHelper {
    public static String parseDate(long timeSTamp) {
        try {
            Date date = new Date(timeSTamp);
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(date);
        } catch (Exception exp) {

        }
        return "";
    }
}
