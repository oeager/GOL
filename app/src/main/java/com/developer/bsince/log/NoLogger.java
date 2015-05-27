package com.developer.bsince.log;

/**
 * Created by oeager on 2015/5/16.
 */
public class NoLogger implements ILog.TagLog {

    @Override
    public void tag(String tag) {

    }

    @Override
    public void v(String message, Object... args) {

    }

    @Override
    public void v(Throwable t, String message, Object... args) {

    }

    @Override
    public void d(String message, Object... args) {

    }

    @Override
    public void d(Throwable t, String message, Object... args) {

    }

    @Override
    public void i(String message, Object... args) {

    }

    @Override
    public void i(Throwable t, String message, Object... args) {

    }

    @Override
    public void w(String message, Object... args) {

    }

    @Override
    public void w(Throwable t, String message, Object... args) {

    }

    @Override
    public void e(String message, Object... args) {

    }

    @Override
    public void e(Throwable t, String message, Object... args) {

    }
}
