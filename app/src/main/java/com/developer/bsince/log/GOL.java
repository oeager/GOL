package com.developer.bsince.log;

import android.util.SparseBooleanArray;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by oeager on 2015/5/16.
 */
public class GOL {

    private GOL(){}

    static final SparseBooleanArray TAGGED_LOGS = new SparseBooleanArray();

    static final List<ILog> LOG_POOL = new CopyOnWriteArrayList<>();

    /** Log a verbose message with optional format args. */
    public static void v(String message, Object... args) {
        LOG.v(message, args);
    }

    /** Log a verbose exception and a message with optional format args. */
    public static void v(Throwable t, String message, Object... args) {
        LOG.v(t, message, args);
    }

    /** Log a debug message with optional format args. */
    public static void d(String message, Object... args) {
        LOG.d(message, args);
    }

    /** Log a debug exception and a message with optional format args. */
    public static void d(Throwable t, String message, Object... args) {
        LOG.d(t, message, args);
    }

    /** Log an info message with optional format args. */
    public static void i(String message, Object... args) {
        LOG.i(message, args);
    }

    /** Log an info exception and a message with optional format args. */
    public static void i(Throwable t, String message, Object... args) {
        LOG.i(t, message, args);
    }

    /** Log a warning message with optional format args. */
    public static void w(String message, Object... args) {
        LOG.w(message, args);
    }

    /** Log a warning exception and a message with optional format args. */
    public static void w(Throwable t, String message, Object... args) {
        LOG.w(t, message, args);
    }

    /** Log an error message with optional format args. */
    public static void e(String message, Object... args) {
        LOG.e(message, args);
    }

    /** Log an error exception and a message with optional format args. */
    public static void e(Throwable t, String message, Object... args) {
        LOG.e(t, message, args);
    }

    public static void wtf(String message, Object... args) {
        LOG.wtf(message, args);
    }
    public static void json(String json) {
        LOG.json(json);
    }
    public static void xml(String xml) {
        LOG.xml(xml);
    }

    public static ILog tag(String tag){
        for (int i =0,size = TAGGED_LOGS.size();i<size;i++){
            ((ILog.TagLog)LOG_POOL.get(TAGGED_LOGS.keyAt(i))).tag(tag);
        }
        return LOG;
    }

    public static void addLog(ILog log){
        if(log==null){
            throw new NullPointerException("log is null");
        }
        if(log ==LOG){
            throw new IllegalArgumentException("Cannot add the static Log of GOl itself.");
        }
        if (log instanceof ILog.TagLog) {
            TAGGED_LOGS.append(LOG_POOL.size(), true);
        }
        LOG_POOL.add(log);
    }

    public static void removeLog(ILog tree) {
        for (int i = 0, size = LOG_POOL.size(); i < size; i++) {
            if (LOG_POOL.get(i) == tree) {
                TAGGED_LOGS.delete(i);
                LOG_POOL.remove(i);
                return;
            }
        }
        throw new IllegalArgumentException("Cannot uproot tree which is not planted: " + tree);
    }

    
    public static void clearLogs() {
        TAGGED_LOGS.clear();
        LOG_POOL.clear();
    }

    private static final ILog LOG = new ILog() {
        @Override
        public void v(String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).v(message,args);
            }
        }

        @Override
        public void v(Throwable t, String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).v(t, message, args);
            }
        }

        @Override
        public void d(String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).d(message, args);
            }
        }

        @Override
        public void d(Throwable t, String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).d(t, message, args);
            }
        }

        @Override
        public void i(String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).i(message, args);
            }
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).i(t, message, args);
            }
        }

        @Override
        public void w(String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).w(message, args);
            }
        }

        @Override
        public void w(Throwable t, String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).w(t, message, args);
            }
        }

        @Override
        public void e(String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).e(message, args);
            }
        }

        @Override
        public void wtf(String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).wtf(message, args);
            }
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).e(t, message, args);
            }
        }

        @Override
        public void json(String json) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).json(json);
            }
        }

        @Override
        public void xml(String xml) {
            for (int i = 0,size = LOG_POOL.size(); i < size; i++) {
                LOG_POOL.get(i).xml(xml);
            }
        }
    };

    public ILog getLogger(){
        if(!LOG_POOL.isEmpty()){
            return LOG_POOL.get(0);
        }
        return LOG;
    }
    public <T extends ILog> T getLogger(Class<T> cls){
        for (int i=0,len=LOG_POOL.size();i<len;i++){
            ILog log = LOG_POOL.get(i);
            if(log.getClass()==cls){
                return (T)log;
            }

        }
        return null;
    }

}