package club.godfather.support.log;

import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
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

    public AndroidLgImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void v(String tag, String msg) {
        println(Log.VERBOSE, tag, msg);
    }

    @Override
    public void v(String tag, String msg, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception e) {
            //ignore
        }
        println(Log.VERBOSE, tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        println(Log.DEBUG, tag, msg);
    }

    @Override
    public void d(String tag, String msg, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception e) {
            //ignore
        }
        println(Log.DEBUG, tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        println(Log.INFO, tag, msg);
    }

    @Override
    public void i(String tag, String msg, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception e) {
            //ignore
        }
        println(Log.INFO, tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        println(Log.WARN, tag, msg);
    }

    @Override
    public void w(String tag, String msg, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception e) {
            //ignore
        }
        println(Log.WARN, tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable e) {
        println(Log.WARN, tag, msg, e);
    }

    @Override
    public void w(String tag, String msg, Throwable e, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception ex) {
            //ignore
        }
        println(Log.WARN, tag, msg, e);
    }

    @Override
    public void e(String tag, String msg) {
        println(Log.ERROR, tag, msg);
    }

    @Override
    public void e(String tag, String msg, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception ex) {
            //ignore
        }
        println(Log.ERROR, tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable e) {
        println(Log.ERROR, tag, msg, e);
    }

    @Override
    public void e(String tag, String msg, Throwable e, Object... objects) {
        try {
            msg = String.format(msg, objects);
        } catch (Exception ex) {
            //ignore
        }
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
    public void addInterceptor(LogInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    @Override
    public void removeInterceptor(LogInterceptor interceptor) {
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
        println(level, tag, msg, null, Thread.currentThread().getStackTrace()[5]);
    }

    private void println(final int level, final String tag, final String msg, final Throwable throwable) {
        println(level, tag, msg, throwable, Thread.currentThread().getStackTrace()[5]);
    }

    private void println(final int level, final String tag, final String msg, final Throwable throwable, @NonNull StackTraceElement element) {
        final LogMessage message = new LogMessage();
        message.level = level;
        message.tag = tag;
        message.content = (throwable == null ? msg : (msg + "\n" + Log.getStackTraceString(throwable)));
        message.time = System.currentTimeMillis();
        message.pid = Process.myPid();
        message.threadId = Thread.currentThread().getId();
        message.threadName = Thread.currentThread().getName();
        message.className = element.getClassName();
        message.methodName = element.getMethodName();
        message.lineNumber = element.getLineNumber();
        message.fileName = element.getFileName();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                printImpl(message);
            }
        }).subscribeOn(LogScheduler.INSTANCE).onErrorComplete().subscribe();
    }

    private void printImpl(LogMessage message) throws Exception {
        if (!isLoggable(message.level)) {
            return;
        }
        String m = message.content;
        int length = m.length();
        if (length <= MAX_LENGTH) {
            p(message);
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
                LogMessage clone = message.clone();
                clone.content = str;
                p(clone);
            }
        }
    }

    private void p(LogMessage message) throws Exception {
        Log.println(message.level, message.tag, message.content);
        client.log(context, message);
    }
}