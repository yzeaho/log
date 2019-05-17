package club.godfather.support.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class LogAidlStub extends LogAidlInterface.Stub {

    private static final String TAG = "LogAidlStub";
    private Context context;

    LogAidlStub(Context context) {
        this.context = context;
    }

    @SuppressLint("LogTagMismatch")
    @Override
    public void log(int level, String tag, String text, long time) {
        if (Lg.isLoggable(level)) {
            List<LogInterceptor> deleteList = new ArrayList<>();
            for (LogInterceptor interceptor : Lg.interceptors()) {
                try {
                    interceptor.proceed(level, tag, text, time);
                } catch (RemoteException e) {
                    Log.w(TAG, e.toString());
                    deleteList.add(interceptor);
                }
            }
            for (LogInterceptor interceptor : deleteList) {
                Lg.removeInterceptor(interceptor);
            }
        }
    }

    @Override
    public void addInterceptor(LogInterceptor interceptor) {
        Lg.addInterceptor(interceptor);
    }

    @Override
    public void removeInterceptor(LogInterceptor interceptor) {
        Lg.removeInterceptor(interceptor);
    }

    @Override
    public void setLevel(int level) {
        Lg.setLevel(level);
    }

    @Override
    public int getLevel() {
        return Lg.getLevel();
    }

    @Override
    public boolean isLoggable(int level) {
        return Lg.isLoggable(level);
    }

    @Override
    public void copy(String filepath) {
        try {
            File dir = context.getFilesDir().getParentFile();
            File srcFile = new File(dir, filepath);
            File toFile = new File(Environment.getDataDirectory(), srcFile.getName());
            Log.i(TAG, "copy " + srcFile + " to " + toFile);
            copy(srcFile, toFile);
        } catch (Exception e) {
            Log.w(TAG, "", e);
        }
    }

    @Override
    public String zip(String filename) {
        try {
            File dir = context.getFilesDir().getParentFile();
            File toFile = new File(Environment.getExternalStorageDirectory(), filename);
            Zip.zip(dir, toFile);
            Log.i(TAG, "zip to " + toFile);
            return toFile.getAbsolutePath();
        } catch (Exception e) {
            Log.w(TAG, "", e);
            return e.toString();
        }
    }

    private void copy(File srcFile, File toFile) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(toFile);
            copy(in, out);
        } finally {
            close(in);
            close(out);
        }
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }

    private void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}