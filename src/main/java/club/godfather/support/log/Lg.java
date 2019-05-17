package club.godfather.support.log;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 日志记录
 *
 * <p>
 * 在Application里面进行设置参数
 * <pre>Lg.setLg(new AndroidLgImpl(this));</pre>
 * 设置日志记录级别
 * <pre>Lg.setLevel(Log.DEBUG);</pre>
 * 通过{@link #d Lg.d()},{@link #i Lg.i()}等方法记录日志
 * 如果需要记录日志到文件，请增加文件记录拦截器{@link FileRecordInterceptor}
 * </p>
 * <p>支持跨进程记录日志，多进程通过aidl实现。</p>
 */
public class Lg {

    private static LgInterface sImpl = new JavaLgInterface();

    /**
     * 设置日志接口的实现类
     *
     * @param impl 实现类
     * @see AndroidLgImpl
     */
    public static void setLg(LgInterface impl) {
        sImpl = impl;
    }

    /**
     * 发送一个{@link android.util.Log#VERBOSE}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     */
    public static void v(String tag, String msg) {
        sImpl.v(tag, msg);
    }

    /**
     * 发送一个{@link android.util.Log#DEBUG}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     */
    public static void d(String tag, String msg) {
        sImpl.d(tag, msg);
    }

    /**
     * 发送一个{@link android.util.Log#INFO}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     */
    public static void i(String tag, String msg) {
        sImpl.i(tag, msg);
    }

    /**
     * 发送一个{@link android.util.Log#WARN}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     */
    public static void w(String tag, String msg) {
        sImpl.w(tag, msg);
    }

    /**
     * 发送一个{@link android.util.Log#WARN}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     * @param e   异常信息
     */
    public static void w(String tag, String msg, Throwable e) {
        sImpl.w(tag, msg, e);
    }

    /**
     * 发送一个{@link android.util.Log#ERROR}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     */
    public static void e(String tag, String msg) {
        sImpl.e(tag, msg);
    }

    /**
     * 发送一个{@link android.util.Log#ERROR}级别的日志消息.
     *
     * @param tag 日志消息的标识
     * @param msg 记录的日志
     * @param e   异常信息
     */
    public static void e(String tag, String msg, Throwable e) {
        sImpl.e(tag, msg, e);
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     */
    public static void addInterceptor(@NonNull LogInterceptor interceptor) {
        sImpl.addInterceptor(interceptor);
    }

    /**
     * 移除拦截器
     *
     * @param interceptor 拦截器
     */
    public static void removeInterceptor(@NonNull LogInterceptor interceptor) {
        sImpl.removeInterceptor(interceptor);
    }

    /**
     * 获取所有拦截器
     *
     * @return 拦截器集合
     */
    public static List<LogInterceptor> interceptors() {
        return sImpl.interceptors();
    }

    /**
     * 设置日志级别
     *
     * @param level 日记级别
     * @see android.util.Log#DEBUG
     * @see android.util.Log#INFO
     * @see android.util.Log#ERROR
     */
    public static void setLevel(int level) {
        sImpl.setLevel(level);
    }

    /**
     * 获取日志级别
     *
     * @return 日记级别
     * @see android.util.Log#DEBUG
     * @see android.util.Log#INFO
     * @see android.util.Log#ERROR
     */
    public static int getLevel() {
        return sImpl.getLevel();
    }

    /**
     * 是否可以记录日志
     *
     * @return true：可以记录日志
     */
    public static boolean isLoggable(int level) {
        return sImpl.isLoggable(level);
    }
}