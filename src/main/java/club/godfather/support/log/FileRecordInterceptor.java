package club.godfather.support.log;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.annotations.NonNull;

/**
 * 日志记录
 * Created by y on 2017/10/30.
 */
public class FileRecordInterceptor extends LogInterceptor.Stub {

    private static final String TAG = "FileRecordInterceptor";
    private File dir;
    private FileRecordOutputStream out;

    public FileRecordInterceptor(@NonNull File dir) {
        this(dir, true);
    }

    public FileRecordInterceptor(@NonNull File dir, boolean deleteEnabled) {
        this.dir = dir;
        if (deleteEnabled) {
            new LogDeleteTask(dir).start();
        }
    }

    @Override
    public void proceed(LogMessage message) {
        try {
            if (out == null || !out.check(message.time)) {
                close(out);
                out = init(dir);
            }
            String text = Lg.formatter().format(message);
            out.write(text.getBytes());
            out.write(0x0A);
            out.flush();
        } catch (Exception e) {
            Log.w(TAG, "", e);
        }
    }

    private FileRecordOutputStream init(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Create folder(" + dir.getAbsolutePath() + ") failed.");
        } else if (dir.isFile() && (!dir.delete() || !dir.mkdirs())) {
            throw new IOException("Failed to delete the file or create folder(" + dir.getAbsolutePath() + ").");
        }
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String prefix = sdf.format(date);
        String name = prefix + ".log";
        File file = new File(dir, name);
        return new FileRecordOutputStream(new FileOutputStream(file, true), date);
    }

    private void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}