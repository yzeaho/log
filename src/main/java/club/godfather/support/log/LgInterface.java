package club.godfather.support.log;

import java.util.List;

/**
 * 日志接口
 * Created by y on 2016/8/26.
 */
public interface LgInterface {

    void v(String tag, String msg);

    void v(String tag, String msg, Object... objects);

    void d(String tag, String msg);

    void d(String tag, String msg, Object... objects);

    void i(String tag, String msg);

    void i(String tag, String msg, Object... objects);

    void w(String tag, String msg);

    void w(String tag, String msg, Object... objects);

    void w(String tag, String msg, Throwable e);

    void w(String tag, String msg, Throwable e, Object... objects);

    void e(String tag, String msg);

    void e(String tag, String msg, Object... objects);

    void e(String tag, String msg, Throwable e);

    void e(String tag, String msg, Throwable e, Object... objects);

    void setLevel(int level);

    int getLevel();

    void addInterceptor(LogInterceptor interceptor);

    void removeInterceptor(LogInterceptor interceptor);

    List<LogInterceptor> interceptors();

    boolean isLoggable(int level);
}