package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/5.
 */
public class ViewObject {
    private Question question;
    private User user;
    private Map<String , Object> objs = new HashMap<>();

    public void set(String key , Object value){
        objs.put(key , value);
    }
    public Object get(String key){
        return objs.get(key);
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
