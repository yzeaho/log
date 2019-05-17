package club.godfather.support.log;

interface LogInterceptor {

    void proceed(int level, String tag, String text, long time);
}