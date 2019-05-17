package club.godfather.support.log;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;

/**
 * android日志记录
 * Created by y on 2016/8/26.
 */
public class AndroidLgImpl implements LgInterface {

    private static final int MAX_LENGTH = 3 * 1024;
    private Context context;
    private int level = Log.INFO;
    private final List<LogInterceptor> interceptors = new CopyOnWriteArrayList<>();
    private final LogClient client = new LogClient();
    private final Scheduler LOG_SCHEDULER = LogScheduler.from();

    public AndroidLgImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void v(String tag, String msg) {
        println(Log.VERBOSE, tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        println(Log.DEBUG, tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        println(Log.INFO, tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        println(Log.WARN, tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable e) {
        println(Log.WARN, tag, msg, e);
    }

    @Override
    public void e(String tag, String msg) {
        println(Log.ERROR, tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable e) {
        println(Log.ERROR, tag, msg, e);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void addInterceptor(@NonNull LogInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    @Override
    public void removeInterceptor(@NonNull LogInterceptor interceptor) {
        interceptors.remove(interceptor);
    }

    @Override
    public List<LogInterceptor> interceptors() {
        return interceptors;
    }

    @Override
    public boolean isLoggable(int level) {
        return level >= this.level;
    }

    private void println(int level, String tag, String msg) {
        println(level, tag, msg, null);
    }

    private void println(final int level, final String tag, final String msg, final Throwable throwable) {
        final long time = System.currentTimeMillis();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                printImpl(level, tag, msg, throwable, time);
            }
        }).subscribeOn(LOG_SCHEDULER).onErrorComplete().subscribe();
    }

    private void printImpl(int level, String tag, String msg, Throwable throwable, long time) throws Exception {
        String m = (throwable == null ? msg : (msg + "\n" + Log.getStackTraceString(throwable)));
        int length = m.length();
        if (length <= MAX_LENGTH) {
            p(level, tag, m, time);
        } else {
            int index = 0;
            String str;
            while (index < length) {
                if (length <= index + MAX_LENGTH) {
                    str = m.substring(index);
                } else {
                    str = m.substring(index, index + MAX_LENGTH);
                }
                index += MAX_LENGTH;
                p(level, tag, str, time);
            }
        }
    }

    private void p(int level, String tag, String text, long time) throws Exception {
        client.log(context, level, tag, text, time);
    }
}