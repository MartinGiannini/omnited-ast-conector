package coop.bancocredicoop.omnited.utils;

import java.util.Date;

public class DateUtils {
    public static long toTimestamp(Date date) {
        return date.getTime();
    }
}