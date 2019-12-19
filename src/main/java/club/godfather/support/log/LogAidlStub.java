package club.godfather.support.log;

import android.annotation.SuppressLint;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class LogAidlStub extends LogAidlInterface.Stub {

    private static final String TAG = "LogAidlStub";

    @SuppressLint("LogTagMismatch")
    @Override
    public void log(LogMessage message) {
        if (Lg.isLoggable(message.level)) {
            List<LogInterceptor> deleteList = new ArrayList<>();
            for (LogInterceptor interceptor : Lg.interceptors()) {
                try {
                    interceptor.proceed(message);
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
}