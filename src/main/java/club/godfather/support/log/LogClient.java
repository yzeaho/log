package club.godfather.support.log;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class LogClient implements ServiceConnection {

    private static final String TAG = "LogClient";
    private LogAidlInterface service;
    private CountDownLatch latch;

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.i(TAG, "onServiceConnected " + name);
        service = LogAidlInterface.Stub.asInterface(binder);
        latch.countDown();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(TAG, "onServiceDisconnected " + name);
        service = null;
    }

    private void connect(Context context) throws InterruptedException {
        Log.i(TAG, "connect");
        latch = new CountDownLatch(1);
        Intent intent = new Intent(context, LogService.class);
        context.bindService(intent, this, Service.BIND_AUTO_CREATE);
        latch.await(30, TimeUnit.SECONDS);
        if (service == null) {
            throw new IllegalStateException("failed to bind service");
        }
        Log.i(TAG, "connect finish");
    }

    @SuppressLint("LogTagMismatch")
    void log(Context context, LogMessage message) throws InterruptedException, RemoteException {
        if (service == null) {
            connect(context);
        }
        if (service.isLoggable(message.level)) {
            service.log(message);
        }
    }
}