package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/10/6.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }
    public void setUser(User user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
