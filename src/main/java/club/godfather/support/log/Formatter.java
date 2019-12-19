package club.godfather.support.log;

import io.reactivex.annotations.NonNull;

public interface Formatter {

    @NonNull
    String format(LogMessage message);
}