package club.godfather.support.log;

import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日志记录
 * Created by y on 2017/10/30.
 */
public class FileRecordInterceptor extends LogInterceptor.Stub {

    private static final String TAG = "FileRecordInterceptor";
    private String dir;
    private BufferedOutputStream out;

    public FileRecordInterceptor(@NonNull String _dir) {
        this(_dir, true);
    }

    public FileRecordInterceptor(@NonNull String _dir, boolean deleteEnabled) {
        dir = _dir;
        if (deleteEnabled) {
            new LogDeleteTask(dir).start();
        }
    }

    @Override
    public void proceed(int level, String tag, String text, long time) {
        try {
            if (out == null) {
                out = init(new File(dir));
            }
            text = formatLog(level, tag, text, time);
            out.write(text.getBytes());
            out.write(0x0A);
            out.flush();
        } catch (Exception e) {
            Log.w(TAG, "", e);
        }
    }

    /**
     * 格式化日志格式
     */
    private String formatLog(int level, String tag, String text, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);
        return String.format("%s %s/%s(%s) [%s] %s", sdf.format(new Date(time)), getLevel(level), tag,
                Process.myPid(), Thread.currentThread().getName(), text);
    }

    private String getLevel(int level) {
        switch (level) {
            case Log.VERBOSE:
                return "V";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            case Log.ASSERT:
                return "A";
            case Log.DEBUG:
            default:
                return "D";
        }
    }

    private BufferedOutputStream init(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Create folder(" + dir.getAbsolutePath() + ") failed.");
        } else if (dir.isFile() && (!dir.delete() || !dir.mkdirs())) {
            throw new IOException("Failed to delete the file or create folder(" + dir.getAbsolutePath() + ").");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String name = sdf.format(new Date()) + ".log";
        File file = new File(dir, name);
        return new BufferedOutputStream(new FileOutputStream(file, true));
    }
}