package com.developer.bsince.log;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** an implementation of Ilog used by android log;
 * @author oeager
 */
public class AndroidLogger implements ILog.TagLog {

    private static final int MAX_LOG_LENGTH = 4000;
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
    private static final ThreadLocal<String> NEXT_TAG = new ThreadLocal<String>();

    private static String createTag() {
        String tag = NEXT_TAG.get();
        if (tag != null) {
            NEXT_TAG.remove();
            return tag;
        }

        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        if (stackTrace.length < 6) {
            throw new IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }
        tag = stackTrace[5].getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        return tag.substring(tag.lastIndexOf('.') + 1);
    }

    static String formatString(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }

    @Override public void v(String message, Object... args) {
        printLog(Log.VERBOSE, formatString(message, args), null);
    }

    @Override public void v(Throwable t, String message, Object... args) {
        printLog(Log.VERBOSE, formatString(message, args), t);
    }

    @Override public void d(String message, Object... args) {
        printLog(Log.DEBUG, formatString(message, args), null);
    }

    @Override public void d(Throwable t, String message, Object... args) {
        printLog(Log.DEBUG, formatString(message, args), t);
    }

    @Override public void i(String message, Object... args) {
        printLog(Log.INFO, formatString(message, args), null);
    }

    @Override public void i(Throwable t, String message, Object... args) {
        printLog(Log.INFO, formatString(message, args), t);
    }

    @Override public void w(String message, Object... args) {
        printLog(Log.WARN, formatString(message, args), null);
    }

    @Override public void w(Throwable t, String message, Object... args) {
        printLog(Log.WARN, formatString(message, args), t);
    }

    @Override public void e(String message, Object... args) {
        printLog(Log.ERROR, formatString(message, args), null);
    }

    @Override public void e(Throwable t, String message, Object... args) {
        printLog(Log.ERROR, formatString(message, args), t);
    }

    private void printLog(int priority, String message, Throwable t) {
        if (message == null || message.length() == 0) {
            if (t != null) {
                message = Log.getStackTraceString(t);
            } else {
                // Swallow message if it's null and there's no throwable.
                return;
            }
        } else if (t != null) {
            message += "\n" + Log.getStackTraceString(t);
        }

        String tag = createTag();

        if (message.length() < MAX_LOG_LENGTH) {
            Log.println(priority, tag, message);
            return;
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                Log.println(priority, tag, message.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }

    @Override public void tag(String tag) {
        NEXT_TAG.set(tag);
    }
}
