package club.godfather.support.log;

import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * 删除放置超过一个月的日志文件
 */
class FileRecordLogDeleteTask implements FilenameFilter, Action {

    private static final String TAG = "FileRecordLogDeleteTask";
    private static final long EXPIRED_TIME = 1000L * 60 * 60 * 24 * 30;
    private File dir;
    private long currentTime;

    FileRecordLogDeleteTask(File dir) {
        this.dir = dir;
    }

    void start() {
        Completable.fromAction(this).subscribeOn(Schedulers.single()).onErrorComplete().subscribe();
    }

    @Override
    public boolean accept(File dir, String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = sdf.parse(name);
            if (date != null && Math.abs(currentTime - date.getTime()) > EXPIRED_TIME) {
                Log.i(TAG, "add file " + name + " to delete");
                return true;
            }
        } catch (Exception e) {
            //ignore
        }
        return false;
    }

    @Override
    public void run() {
        if (dir == null) {
            return;
        }
        currentTime = System.currentTimeMillis();
        File[] fs = dir.listFiles(this);
        if (fs != null) {
            for (File f : fs) {
                //noinspection ResultOfMethodCallIgnored
                f.delete();
            }
        }
    }
}