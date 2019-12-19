package club.godfather.support.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.annotations.NonNull;

public class AndroidFormatter implements Formatter {

    @NonNull
    @Override
    public String format(LogMessage message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
        return String.format("%s %s/%s(%s)[%s]%s", sdf.format(new Date(message.time)),
                levelToString(message.level),
                message.tag,
                message.threadId,
                message.threadName,
                message.content);
    }

    public static String levelToString(int level) {
        switch (level) {
            case Log.VERBOSE:
                return "V";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            case Log.ASSERT:
                return "A";
            case Log.DEBUG:
            default:
                return "D";
        }
    }
}