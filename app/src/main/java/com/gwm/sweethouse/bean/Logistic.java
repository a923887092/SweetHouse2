package com.gwm.sweethouse.bean;

/**
 * Created by Administrator on 2015/11/4.
 */
public class Logistic {
    String logContext;
    String logTime;

    public String getLogContext() {
        return logContext;
    }

    public void setLogContext(String logContext) {
        this.logContext = logContext;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public Logistic(String logContext, String logTime) {
        this.logContext = logContext;
        this.logTime = logTime;
    }
    public Logistic() {

    }

    @Override
    public String toString() {
        return "Logistic{" +
                "logContext='" + logContext + '\'' +
                ", logTime='" + logTime + '\'' +
                '}';
    }
}
