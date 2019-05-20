package club.godfather.support.log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LogService extends Service {

    private static final String TAG = "LogService";
    private LogAidlStub stub;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate " + this);
        stub = new LogAidlStub(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand " + intent);
        if (intent != null && intent.hasExtra("_level")) {
            Lg.setLevel(intent.getIntExtra("_level", Log.INFO));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy " + this);
    }
}