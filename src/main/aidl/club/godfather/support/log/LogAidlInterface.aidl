package club.godfather.support.log;

import club.godfather.support.log.LogInterceptor;

interface LogAidlInterface {

    void log(int level, String tag, String text, long time);

    void addInterceptor(LogInterceptor interceptor);

    void removeInterceptor(LogInterceptor interceptor);

    void setLevel(int level);

    int getLevel();

    boolean isLoggable(int level);

    void copy(String filepath);

    String zip(String filename);
}