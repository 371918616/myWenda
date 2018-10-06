package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2018/10/5.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public Map<String, String> register(String username , String password){
        Map<String , String> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msg" , "用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg" , "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user != null){
            map.put("msg" , "用户名已经被注册");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0 , 5));
        Random random = new Random();
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png" , random.nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket" , ticket);

        return map;

    }

    public User getUser(int id){
        return userDAO.selectById(id);
    }

    public Map<String,String> login(String username, String password) {
        Map<String , String> map = new HashMap<>();
        if(StringUtils.isEmpty(username)){
            map.put("msg" , "用户名不能为空");
            return map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msg" , "密码不能为空");
            return map;
        }
        User user = userDAO.selectByName(username);
        if(user == null){
            map.put("msg" , "用户不存在");
            return map;
        }
        if(!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msg" , "密码错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket" , ticket);


        return map;
    }

    public String addLoginTicket(int userId){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600*24*100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-" , ""));
        loginTicketDAO.addTicket(loginTicket);

        return loginTicket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket , 1);
    }
}
