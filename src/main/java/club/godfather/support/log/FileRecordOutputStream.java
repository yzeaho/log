package club.godfather.support.log;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

class FileRecordOutputStream extends BufferedOutputStream {

    private final Calendar calendar;

    FileRecordOutputStream(OutputStream out, Date date) {
        super(out);
        calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    boolean check(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return calendar.get(Calendar.YEAR) == c.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == c.get(Calendar.MONTH)
                && calendar.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH);
    }
}