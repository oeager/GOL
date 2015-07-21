package com.developer.bsince.log;

/**
 * Created by oeager on 2015/5/16.
 *
 * Responsible for providing android classes with a platform neutral way of logging.
 *
 * @author oeager
 * @since 1.0.3
 *
 */
public interface ILog {

    void v(String message, Object... args);

    void v(Throwable t, String message, Object... args);

    void d(String message, Object... args);

    void d(Throwable t, String message, Object... args);

    void i(String message, Object... args);

    void i(Throwable t, String message, Object... args);

    void w(String message, Object... args);

    void w(Throwable t, String message, Object... args);

    void e(String message, Object... args);

    void wtf(String message, Object... args);

    void e(Throwable t, String message, Object... args);

    void json(String json);

    void xml(String xml);

    public static interface TagLog extends ILog {
        void tag(String tag);
    }
}
