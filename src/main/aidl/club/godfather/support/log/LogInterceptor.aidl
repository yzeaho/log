package club.godfather.support.log;

import club.godfather.support.log.LogMessage;

interface LogInterceptor {

    void proceed(in LogMessage message);
}