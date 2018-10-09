package com.nowcoder.async;

import java.util.List;

/**
 * Created by Administrator on 2018/10/9.
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);

    List<EventType> getSupportEventTypes();
}
