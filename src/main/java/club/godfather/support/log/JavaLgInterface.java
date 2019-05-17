package club.godfather.support.log;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JavaLgInterface implements LgInterface {

    private int level = Log.INFO;
    private List<LogInterceptor> interceptors = new CopyOnWriteArrayList<>();

    @Override
    public void v(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void d(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void i(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void w(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void w(String tag, String msg, Throwable e) {
        System.out.println(tag + " " + msg);
        e.printStackTrace();
    }

    @Override
    public void e(String tag, String msg) {
        System.out.println(tag + " " + msg);
    }

    @Override
    public void e(String tag, String msg, Throwable e) {
        System.out.println(tag + " " + msg);
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
        return this.level <= level;
    }
}