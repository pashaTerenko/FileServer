package com.terenko.fileserver.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public  class Action {

    protected static final Logger serverLogger = LoggerFactory.getLogger(Action.class.getClass().getName());
    protected Date time;
    protected String exeptionMessage;

    @Override
    public String toString() {
        return "Action{" +
                "time=" + time +
                ", exeptionMessage='" + exeptionMessage + '\'' +
                '}';
    }
}
