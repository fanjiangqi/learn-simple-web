package com.fanjiangqi.asyn;

import java.util.List;

/**
 * Created by fanjiangqi on 2017/4/29.
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}
