package club.godfather.support.log;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LgTest {

    private static final String TAG = "LgTest";

    @Test
    public void testLogV() throws InterruptedException {
        Context context = InstrumentationRegistry.getTargetContext();
        Lg.setLg(new AndroidLgImpl(context));
        Lg.setLevel(Log.VERBOSE);
        Lg.d(TAG, "log message");
        Lg.w(TAG, "log message", new RuntimeException());
        File dir = new File(context.getFilesDir(), "log");
        Lg.addInterceptor(new FileRecordInterceptor(dir));
        Thread.sleep(1000 * 60 * 60);
    }
}