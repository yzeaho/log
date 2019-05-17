package club.godfather.support.log;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 日志接口
 * Created by y on 2016/8/26.
 */
public interface LgInterface {

    void v(String tag, String msg);

    void d(String tag, String msg);

    void i(String tag, String msg);

    void w(String tag, String msg);

    void w(String tag, String msg, Throwable e);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable e);

    void setLevel(int level);

    int getLevel();

    void addInterceptor(@NonNull LogInterceptor interceptor);

    void removeInterceptor(@NonNull LogInterceptor interceptor);

    List<LogInterceptor> interceptors();

    boolean isLoggable(int level);
}