package club.godfather.support.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

class LogScheduler {

    @NonNull
    private static Scheduler from() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "LogScheduler");
                t.setPriority(Thread.MIN_PRIORITY);
                return t;
            }
        };
        //noinspection UnstableApiUsage
        return Schedulers.from(Executors.newSingleThreadExecutor(threadFactory), false);
    }

    static final Scheduler INSTANCE = from();
}