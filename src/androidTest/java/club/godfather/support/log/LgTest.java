package club.godfather.support.log;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LgTest {

    @Test
    public void testLogV() throws InterruptedException {
        Context context = InstrumentationRegistry.getTargetContext();
        Lg.setLg(new AndroidLgImpl(context));
        Lg.setLevel(Log.VERBOSE);
        Lg.d("A", "a");
        Thread.sleep(1000 * 60 * 60);
    }
}