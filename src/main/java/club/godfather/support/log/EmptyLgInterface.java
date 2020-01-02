package club.godfather.support.log;

import java.util.ArrayList;
import java.util.List;

public class EmptyLgInterface implements LgInterface {

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
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void addInterceptor(LogInterceptor interceptor) {
    }

    @Override
    public void removeInterceptor(LogInterceptor interceptor) {
    }

    @Override
    public List<LogInterceptor> interceptors() {
        return null;
    }

    @Override
    public boolean isLoggable(int level) {
        return false;
    }
}