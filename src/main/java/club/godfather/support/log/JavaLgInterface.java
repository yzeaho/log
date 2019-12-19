package club.godfather.support.log;

import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JavaLgInterface implements LgInterface {

    private int level = Log.INFO;
    private List<LogInterceptor> interceptors = new CopyOnWriteArrayList<>();
    private Formatter formatter = new Formatter() {
        @Override
        public String format(LogMessage message) {
            return message.content;
        }
    };

    @Override
    public void v(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void v(String tag, String msg, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
    }

    @Override
    public void d(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void d(String tag, String msg, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
    }

    @Override
    public void i(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void i(String tag, String msg, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
    }

    @Override
    public void w(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void w(String tag, String msg, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
    }

    @Override
    public void w(String tag, String msg, Throwable e) {
        System.out.println(tag + " " + msg);
        e.printStackTrace();
    }

    @Override
    public void w(String tag, String msg, Throwable e, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
        e.printStackTrace();
    }

    @Override
    public void e(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void e(String tag, String msg, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
    }

    @Override
    public void e(String tag, String msg, Throwable e) {
        System.out.println(tag + " " + msg);
        e.printStackTrace();
    }

    @Override
    public void e(String tag, String msg, Throwable e, Object... objects) {
        System.out.println(tag + " " + String.format(msg, objects));
        e.printStackTrace();
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
        return this.level <= level;
    }

    @Override
    public Formatter formatter() {
        return formatter;
    }
}