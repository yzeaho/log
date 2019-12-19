package club.godfather.support.log;

import android.os.Parcel;
import android.os.Parcelable;

public class LogMessage implements Cloneable, Parcelable {
    public int level;
    public String tag;
    public String content;
    public long time;
    public long pid;
    public long threadId;
    public String threadName;
    public String className;
    public String methodName;
    public int lineNumber;
    public String fileName;

    public LogMessage() {
    }

    protected LogMessage(Parcel in) {
        level = in.readInt();
        tag = in.readString();
        content = in.readString();
        time = in.readLong();
        pid = in.readLong();
        threadId = in.readLong();
        threadName = in.readString();
        className = in.readString();
        methodName = in.readString();
        lineNumber = in.readInt();
        fileName = in.readString();
    }

    public static final Creator<LogMessage> CREATOR = new Creator<LogMessage>() {
        @Override
        public LogMessage createFromParcel(Parcel in) {
            return new LogMessage(in);
        }

        @Override
        public LogMessage[] newArray(int size) {
            return new LogMessage[size];
        }
    };

    @Override
    public LogMessage clone() {
        try {
            return (LogMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            LogMessage m = new LogMessage();
            m.level = level;
            m.tag = tag;
            m.content = content;
            m.time = time;
            m.pid = pid;
            m.threadId = threadId;
            m.threadName = threadName;
            m.className = className;
            m.methodName = methodName;
            m.lineNumber = lineNumber;
            m.fileName = fileName;
            return m;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(level);
        dest.writeString(tag);
        dest.writeString(content);
        dest.writeLong(time);
        dest.writeLong(pid);
        dest.writeLong(threadId);
        dest.writeString(threadName);
        dest.writeString(className);
        dest.writeString(methodName);
        dest.writeInt(lineNumber);
        dest.writeString(fileName);
    }
}