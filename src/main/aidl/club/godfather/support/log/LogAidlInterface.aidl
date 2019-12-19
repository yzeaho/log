package club.godfather.support.log;

import club.godfather.support.log.LogInterceptor;
import club.godfather.support.log.LogMessage;

interface LogAidlInterface {

    void log(in LogMessage message);

    void addInterceptor(LogInterceptor interceptor);

    void removeInterceptor(LogInterceptor interceptor);

    void setLevel(int level);

    int getLevel();

    boolean isLoggable(int level);
}