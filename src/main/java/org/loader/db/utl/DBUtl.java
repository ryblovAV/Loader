package org.loader.db.utl;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

public class DBUtl{

    private final static Logger logger = Logger.getLogger(DBUtl.class.getName());

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Date getDate(int year, int month, int day) {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

    public static Timestamp strToTimestamp(String str) {

        try {
            Date date = dateFormat.parse(str);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            logger.severe("Error in date [" + str + "]");
        }
        return null;
    }


}
